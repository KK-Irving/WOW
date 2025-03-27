# WOW

WOW (WakeOnWAN) 是一个基于 Android 的应用，提供了通过发送魔术包（Magic Packet）来实现 Wake on LAN（远程唤醒）的功能。用户可以通过输入端口号、MAC 地址和 IP 地址（广播地址）来自定义唤醒参数，方便在不同场景下测试和使用。

## 功能说明

- **动态参数输入**：通过三个输入框分别输入端口号、MAC 地址和 IP 地址（广播地址）。
- **魔术包发送**：根据用户输入的信息，生成符合 Wake on LAN 标准格式的魔术包，并通过 UDP 广播发送出去。
- **异步处理**：网络操作在子线程中执行，保证不会阻塞主线程，同时发送成功或失败通过 Toast 提示用户。

## 使用方法

1. **配置环境**  
   - 使用 [Android Studio](https://developer.android.com/studio) 打开工程。
   - 确保你的 Android 项目配置了最低 SDK 版本（例如 API 21 及以上）。

2. **修改参数**  
   - 默认代码中使用的端口、MAC 地址和 IP 地址可在应用内通过输入框动态设置，无需手动修改代码。

3. **构建项目**  
   - 在 Android Studio 中点击 "Build" -> "Make Project" 进行项目构建。
   - 构建成功后，可生成 APK 文件。

4. **安装与测试**  
   - 将生成的 APK 安装到你的 Android 设备上（使用 USB 调试或通过文件传输）。
   - 打开应用后，在对应的输入框中填写：
     - **端口号**：例如 `1234`
     - **MAC 地址**：例如 `1c:2f:a2:f6:fd:32`
     - **IP 地址**：例如 `192.168.1.255`
   - 点击 “发送魔术包” 按钮，应用会在后台发送魔术包，并通过 Toast 提示是否发送成功。

## 项目结构

该项目的主要文件结构如下：
- **app/**  
  - **src/main/java/com/example/wow/**  
    - `MainActivity.java`：主 Activity 文件，包含用户界面和魔术包发送逻辑。  
  - **src/main/res/layout/**  
    - `activity_main.xml`：应用界面布局文件，定义了三个输入框和一个按钮。  
  - **src/main/res/values/**  
    - `styles.xml`：样式文件，可定义应用主题（默认或自定义均可）。  
  - `AndroidManifest.xml`：应用配置文件，声明了所需权限及 MainActivity 的配置。  
- **build.gradle**（模块级）和 **build.gradle**（项目级）：Gradle 配置文件，用于构建项目。

## 注意事项

- 请确保目标设备支持 Wake on LAN 功能，且网络配置正确。  
- 如果需要使用自定义主题，请在 `styles.xml` 中定义相应主题，或修改 AndroidManifest.xml 中引用系统内置主题。

## 参考链接

- [Wake on LAN 相关介绍](https://en.wikipedia.org/wiki/Wake-on-LAN)

---

通过该应用，你可以方便地测试不同网络环境下的远程唤醒功能，也可以作为学习 Android 网络编程的示例项目。
