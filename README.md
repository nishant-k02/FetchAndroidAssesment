# 📱 FetchDataApp

A simple Android app built with **Java** that fetches JSON data from a remote server and displays it in a clean, grouped, and sorted list using **RecyclerView**.

---

## ✅ Features

- Fetches JSON data from: `https://fetch-hiring.s3.amazonaws.com/hiring.json`
- Filters out items where `"name"` is blank, null, or whitespace
- Groups items by `listId`
- Sorts items by `listId` and then alphabetically by `name`
- Displays the final data in a user-friendly UI using RecyclerView

---


## 🧱 Project Structure

```
app/
├── java/
│   └── com.example.fetchdataapp/
│       ├── MainActivity.java
│       ├── Item.java
│       └── ItemAdapter.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   └── item_layout.xml
│   └── values/
│       └── themes.xml
├── AndroidManifest.xml
└── build.gradle
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio (latest stable version)
- Java 8 or higher
- Android SDK (API 30 or higher recommended)

### Installation Steps

1. **Clone this repo**
   ```bash
   git clone https://github.com/your-username/FetchDataApp.git
   ```

2. **Open in Android Studio**
   - File → Open → Navigate to the project folder

3. **Build the project**
   - Click **Build > Make Project**

4. **Run the app**
   - Use an emulator or connected device

---

## 🛠 Key Libraries Used

- [OkHttp](https://square.github.io/okhttp/) – For network calls
- [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview) – To display list of items
- AndroidX and Material Components

---

## 🧪 Testing

- App has been tested on API 30+ emulator and real Android devices.
- Handles edge cases like:
  - `"name": null`
  - `"name": ""`
  - `"name": "   "`

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Nishant Khandhar**  
📧 nishantkhandhar.us@gmail.com  
🌐 [LinkedIn](https://linkedin.com/in/nishant-khandhar) | [GitHub](https://github.com/nishant-k02)
