# Endfield Industries QR Utilities 📷🔲  
**A clean, fast, no-nonsense QR Code Scanner & Generator for Android**

Endfield Industries QR Utilities is a lightweight Android app built with modern Android tools that lets you **scan** and **generate QR codes** quickly, with a polished UI and zero unnecessary bloat.

---

## ✨ Features

### 📸 QR Code Scanning
- Fast CameraX-based scanning
- Smooth animated scan line
- Auto-copy scanned content to clipboard
- Optional vibration feedback
- Clickable links (open URLs directly)
- Scan results saved automatically to history

### 🧾 QR Code Generation
- Generate QR codes from any text or URL
- Instant preview
- Generated codes are saved to history

### 🗂 History
- Separate tabs for:
  - **Scanned QR codes**
  - **Generated QR codes**
- Stored locally using Room database
- Simple, readable list layout

### ⚙️ Settings
- Toggle auto-copy
- Toggle vibration feedback
- Designed to stay minimal and unobtrusive

---

## 🎨 Design Philosophy

QRSnG is designed to be:
- **Minimal** – only features that matter
- **Fast** – no ads, no trackers, no nonsense
- **Polished** – subtle animations, Material 3 styling
- **Offline-first** – everything works without internet

Color palette:
- **Pine Teal** `#004C49`
- **Blue Slate** `#3F6E8A`
- **Ink Black** `#0D161F`

---

## 🛠 Tech Stack

- **Language:** Kotlin
- **UI:** XML + Material 3
- **Architecture:** Fragments + Navigation Component
- **Camera:** CameraX
- **QR Scanning:** ML Kit Barcode Scanning
- **QR Generation:** ZXing
- **Database:** Room (with KSP)
- **Async:** Kotlin Coroutines
- **Persistence:** SharedPreferences

---

## 📱 Screens & Navigation

- Home
- Scan
- Generate
- History (Tabbed: Scanned / Generated)
- Settings

Navigation is handled using the **Jetpack Navigation Component** with a bottom navigation bar.

---

## 🚀 Getting Started

Follow these steps to set up and run **QRSnG** locally.

### 📦 Prerequisites

- **Android Studio** (latest stable version recommended)
- **Android device or emulator**
  - A physical device is recommended for QR scanning
  - *(Camera access is required)*

### 🔧 Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Velainary/QRSnG.git
2. **Open the project**

- Launch Android Studio

- Select Open

- Navigate to the cloned QRSnG folder

3. **Sync Gradle**

- Allow Android Studio to download dependencies and finish syncing

- Resolve any prompts if shown (SDK versions, plugins, etc.)

4. **Run the app**

- Connect a physical Android device or start an emulator

- Click Run ▶ in Android Studio

- Select your target device

The app should now build and launch successfully.
