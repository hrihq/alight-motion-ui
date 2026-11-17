# REA evidence — Alight Motion

Sumber: XAPK Alight Motion 5.0.273.1028425 (`com.alightcreative.motion`),
diunduh dari APKPure. Base APK diekstrak dari XAPK lalu dianalisis REA.

## Identitas

```
package        com.alightcreative.motion
version        5.0.273.1028425 (1028425)
sha256 base    dac82ea99bb5456c9ea6c305aa39e407c7327b9822a17c5ac9a1676dd79e169c
min / target   SDK 24 / 36
engine         jadx-headless-mcp 0.7.1, apktool 3.0.3, JDK 17
```

## File besar (tidak ikut ke Git, >100 MB)

```
artifacts/alight-motion.xapk          190 MB — XAPK asli
artifacts/alight-motion-base.apk      138 MB — input REA
artifacts/tools/                      JDK 17 + JADX MCP + apktool
```

`artifacts/` ada di `.gitignore`. Letaknya permanen di mesin ini, tidak
terkena pembersihan scratch Hermes (24 h).

## Hasil REA

`rea-alight.evidence.json` — `inspect-android-package`:
- launcher: `com.alightcreative.app.motion.activities.main.MainActivity`
- 38.649 class, 3.849 resource
- komponen: DeviceMismatchNoticeActivity, OpenSourceLibrariesActivity,
  StackedOffersPaywallActivity, MainActivity

`rea-Main.evidence.json` / `rea-NavStyle.evidence.json` — `inspect-android-class`:
- `NavigationBarStyle` enum, satu nilai: `RESKIN`
- MainActivity memakai Compose (banyak `@Composable` ter-obfuscate)

`rea-strings.json` — `decode-android-resources`:
- `tab_bar_home` = Home
- `tab_bar_projects` = Projects
- `tab_bar_templates` = Your Templates
- `tab_bar_tutorials` = Tutorials
- `tab_bar_creators` = Creators
- `tab_bar_elements` = Elements
- `app_name` = Alight Motion

`decoded/res/layout/activity_home_screen.xml`:
- tab frame: `tab_home`, `tab_ranking`, `tab_tutorial`, `tab_projects`, `tab_templates`
- `tabsSpacer` 72 dp → tinggi area navbar
- `topBar` 57 dp, `composableMainContent` (ComposeView), drawer `nav_view`

Warna (`decoded/res/values/colors.xml`):
- `main_background` `#FFF6F6F6`
- `K2` `#FF0F1418`
- `amBackground` `#1F233A`

## Kesimpulan

Navbar asli = Compose Material3 (`NavigationBar` / `ExpressiveNavigationBar`
hadir di DEX), bukan miuix. Strukturnya: 5 tab — Home, Creators, Tutorials,
Projects, Your Templates — dengan tinggi sekitar 72 dp. Rencana rebase:
pertahankan 5 tab, urutan, label dan warna, ganti komponennya dengan miuix
`FloatingNavigationBar` / `FloatingNavigationBarItem`.

Batasan: analisis statis. Tidak ada eksekusi APK, tidak ada verifikasi
tanda tangan, dan dekompilasi method tertentu kena deadline 120 s JADX.
