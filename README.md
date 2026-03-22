# AKEF QR Utilities

[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3.0-purple.svg)](https://kotlinlang.org)
[![Min SDK](https://img.shields.io/badge/Min%20SDK-24-orange.svg)](https://developer.android.com/studio/releases/platforms)

A modern Android application for QR code scanning and generation with local history tracking. Built with Kotlin, CameraX, ML Kit, and Room database.

## 📱 Features

### 🔍 QR Code Scanning
- **Real-time Camera Integration**: Uses CameraX for smooth, high-performance camera access
- **ML Kit Barcode Detection**: Powered by Google's ML Kit for accurate QR code recognition
- **Visual Feedback**: Animated scan line and haptic vibration on successful detection
- **Instant Copy**: One-tap copy to clipboard functionality
- **Material Design UI**: Clean, intuitive interface with card-based result display

### 🎯 QR Code Generation
- **Text-to-QR**: Generate QR codes from any text input
- **High-Quality Output**: 600x600 pixel QR codes using ZXing library
- **Automatic History**: Generated codes are automatically saved to history
- **Fast Processing**: Background thread processing with coroutine support

### 📚 History Management
- **Dual Tabs**: Separate "Scanned" and "Generated" history sections
- **Persistent Storage**: Room database for reliable data persistence
- **Timestamp Tracking**: All entries include creation timestamps
- **Efficient Display**: RecyclerView with DiffUtil for smooth scrolling

### ⚙️ Settings
- **Auto-Copy Toggle**: Automatically copy scanned QR content to clipboard
- **Vibration Control**: Enable/disable haptic feedback
- **History Management**: Clear all history with one tap
- **Persistent Preferences**: Settings saved across app sessions

### 🧭 Navigation
- **Bottom Navigation**: 5 main sections: Home, Scan, Generate, History, Settings
- **Fragment-Based**: AndroidX Navigation component for smooth transitions
- **Single Activity**: Modern single-activity architecture

## 📸 Screenshots

*Screenshots will be added here*

## 🚀 Installation

### Prerequisites
- **Android Studio**: Arctic Fox or later (recommended: latest stable)
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 36 (Android 15)
- **Java Compatibility**: JDK 11

### Build Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/akef-qr-utilities.git
   cd akef-qr-utilities
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Sync Project**:
   - Android Studio will automatically sync Gradle files
   - If prompted, install any missing SDK components

4. **Build and Run**:
   - Connect an Android device or start an emulator
   - Click the "Run" button (green play icon) in Android Studio
   - Select your target device and install the app

### Alternative: Command Line Build

```bash
# Using Gradle wrapper
./gradlew assembleDebug  # Build debug APK
./gradlew installDebug   # Install on connected device
```

## 📖 Usage

### Scanning QR Codes
1. Navigate to the **Scan** tab
2. Grant camera permissions when prompted
3. Point your camera at a QR code
4. The app will automatically detect and display the content
5. Tap the copy button to copy the content to clipboard

### Generating QR Codes
1. Go to the **Generate** tab
2. Enter the text you want to encode
3. Tap the "Generate" button
4. The QR code will be displayed and automatically saved to history

### Viewing History
1. Access the **History** tab
2. Switch between "Scanned" and "Generated" tabs
3. Browse your QR code history
4. Tap any item to view details

### Customizing Settings
1. Open the **Settings** tab
2. Toggle auto-copy and vibration preferences
3. Clear history if needed

## 🏗️ Architecture

### Overview
AKEF QR Utilities follows modern Android development practices with a clean architecture approach:

```
├── UI Layer (Fragments)
├── Navigation Layer (AndroidX Navigation)
├── Data Layer (Room Database)
└── Business Logic (ViewModels & Use Cases)
```

### Key Components

#### MainActivity
- **Purpose**: Main entry point and navigation host
- **Features**: Bottom navigation setup, fragment management
- **Architecture**: Single Activity Architecture with Fragment-based navigation

#### Database Layer
- **QrDatabase**: Room database singleton with thread-safe initialization
- **QrHistoryEntity**: Data model for QR history entries
- **QrHistoryDao**: Data Access Object with Flow-based queries

#### Camera Integration
- **CameraX**: Lifecycle-aware camera management
- **ML Kit**: Real-time barcode detection
- **Performance**: Optimized frame processing with backpressure strategy

#### UI Components
- **Fragments**: Modular UI components
- **View Binding**: Type-safe view access
- **Material Design**: Consistent design language
- **RecyclerView**: Efficient list display with DiffUtil

### Data Flow
1. **User Interaction** → Fragment
2. **Business Logic** → ViewModel (if needed)
3. **Data Operations** → DAO → Room Database
4. **UI Updates** → Flow.collect() → RecyclerView updates

## 📦 Dependencies

### Core AndroidX Libraries
```kotlin
// app/build.gradle.kts
dependencies {
    // Core AndroidX
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.activity:activity-ktx:1.12.3")
    
    // UI Components
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    
    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.9.7")
    
    // Camera & ML
    implementation("androidx.camera:camera-core:1.5.3")
    implementation("androidx.camera:camera-camera2:1.5.3")
    implementation("androidx.camera:camera-lifecycle:1.5.3")
    implementation("androidx.camera:camera-view:1.5.3")
    implementation("com.google.mlkit:barcode-scanning:17.3.0")
    
    // QR Generation
    implementation("com.google.zxing:core:3.5.4")
    
    // Database
    implementation("androidx.room:room-runtime:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")
    ksp("androidx.room:room-compiler:2.8.4")
}
```

### Key Dependencies Explained
- **CameraX**: Modern camera API for Android
- **ML Kit**: Google's machine learning SDK for barcode scanning
- **ZXing**: Popular QR code generation library
- **Room**: SQLite abstraction layer with compile-time verification
- **Navigation**: Fragment navigation with type safety

## 🔧 Configuration

### Build Configuration
```kotlin
// app/build.gradle.kts
android {
    namespace = "com.example.qrsng"
    compileSdk = 36
    
    defaultConfig {
        applicationId = "com.example.qrsng"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    
    buildFeatures {
        viewBinding = true
    }
}
```

### Manifest Permissions
```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-permission android:name="android.permission.VIBRATE" />

<uses-feature
    android:name="android.hardware.camera.any"
    android:required="true" />
```

## 🧪 Testing

### Unit Tests
```bash
./gradlew testDebugUnitTest
```

### Instrumentation Tests
```bash
./gradlew connectedDebugAndroidTest
```

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Development Guidelines
- Follow Kotlin coding conventions
- Use meaningful commit messages
- Add tests for new features
- Update documentation as needed
- Ensure compatibility with min SDK 24

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Google ML Kit](https://developers.google.com/ml-kit) for barcode scanning
- [ZXing](https://github.com/zxing/zxing) for QR code generation
- [AndroidX](https://developer.android.com/jetpack/androidx) libraries
- [Material Design](https://material.io/design) for UI inspiration

---

**Made with ❤️ for the Android community**
