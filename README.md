# Motion Studio

Aplikasi editor video ala Alight Motion, dibangun dengan Jetpack Compose.
Fokus utama: navbar mengapung memakai `FloatingNavigationBar` dari
[miuix](https://github.com/compose-miuix-ui/miuix).

## Stack

- Kotlin 2.4.20 + Jetpack Compose (AGP 9.2.1, Gradle 9.4.1)
- `top.yukonga.miuix.kmp:miuix-ui-android:0.9.4` — navbar, tema, komponen dasar
- compileSdk 37, minSdk 33, targetSdk 36
- Material 3 untuk layar, miuix untuk navbar

## Fitur

- Navbar miuix mengapung: Beranda, Proyek, tombol buat, Aset, Profil
- Perpindahan halaman dengan animasi slide + fade
- Buat proyek baru lewat bottom sheet
- Daftar proyek, efek/aset, pengaturan tema terang/gelap

## Build

Build berjalan di GitHub Actions (tidak perlu SDK/JDK lokal):

```
push ke main -> .github/workflows/build.yml -> assembleDebug + assembleRelease
```

Artifact APK: `motion-studio-debug-apk` dan `motion-studio-release-apk`.
