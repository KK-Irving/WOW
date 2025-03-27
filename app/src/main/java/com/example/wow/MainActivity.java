package com.example.wow;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private EditText portEditText;
    private EditText macEditText;
    private EditText ipEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取布局中的输入框
        portEditText = findViewById(R.id.port_edittext);
        macEditText = findViewById(R.id.mac_edittext);
        ipEditText = findViewById(R.id.ip_edittext);
        Button wakeButton = findViewById(R.id.wake_button);

        wakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 从输入框中获取用户输入
                String portStr = portEditText.getText().toString().trim();
                String macStr = macEditText.getText().toString().trim();
                String ipStr = ipEditText.getText().toString().trim();

                if (portStr.isEmpty() || macStr.isEmpty() || ipStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请填写所有字段", Toast.LENGTH_SHORT).show();
                    return;
                }

                int port;
                try {
                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "端口格式错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 在子线程中执行网络操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            sendMagicPacket(macStr, ipStr, port);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "魔术包已发送", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "发送失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 发送魔术包，用于唤醒支持 Wake on LAN 功能的设备
     *
     * @param macAddress     目标设备的MAC地址
     * @param internetAddress 广播地址或目标IP地址
     * @param port           端口号
     * @throws Exception 异常抛出
     */
    public static void sendMagicPacket(String macAddress, String internetAddress, int port) throws Exception {
        byte[] bytes = new byte[102];
        String[] macParts = macAddress.split(":");

        // 前6个字节填充为 0xff
        for (int i = 0; i < 6; i++) {
            bytes[i] = (byte) 0xff;
        }

        // 接下来复制16次MAC地址
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 6; j++) {
                bytes[6 + (i * 6) + j] = (byte) Integer.parseUnsignedInt(macParts[j], 16);
            }
        }

        InetAddress address = InetAddress.getByName(internetAddress);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        DatagramSocket socket = new DatagramSocket();
        socket.send(packet);
        socket.close();
    }
}

