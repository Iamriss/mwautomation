# mwaautomation — Simple Apps SQA Automation

Project praktikum berseri Bootcamp SQA Automation with AI Development Batch 30. Repository yang sama dipakai untuk praktikum TestNG, Selenium, RestAssured, dan Cucumber.

## Teknologi
- Java 21
- Maven 3.9+
- TestNG 7.10.2
- Selenium 4.28.0 (Selenium Manager)
- RestAssured 5.5.0
- Cucumber 7.20.1 + cucumber-testng + PicoContainer

## Struktur project
```text
mwaautomation/
├── pom.xml
├── testng.xml
├── src/main/java/id/co/juaracoding/util/TextUtil.java
└── src/test/
    ├── java/id/co/juaracoding/
    │   ├── testng/TextUtilTest.java
    │   ├── util/TestConfig.java
    │   ├── selenium/
    │   │   ├── BaseSeleniumTest.java
    │   │   ├── LoginTest.java
    │   │   ├── RegistrasiTest.java
    │   │   ├── LupaPasswordTest.java
    │   │   └── pages/{BasePage,LoginPage,RegisterPage,ForgotPasswordPage}.java
    │   ├── restassured/
    │   │   ├── BaseRestAssuredTest.java
    │   │   ├── ApiTestData.java
    │   │   ├── LoginApiTest.java
    │   │   ├── RegisterApiTest.java
    │   │   ├── ForgotPasswordApiTest.java
    │   │   └── util/RsaHelper.java
    │   └── cucumber/
    │       ├── web/{WebDriverContext,WebHooks,WebSteps,CucumberWebRunner}.java
    │       └── api/{ApiSteps,CucumberApiRunner}.java
    └── resources/features/{web,api}/*.feature
```

## 1. Persiapan aplikasi Simple Apps
1. Pastikan JDK 21 dan Maven 3.9+ terpasang.
2. Letakkan `simple-apps-backend-0.0.1-SNAPSHOT.jar` di folder terpisah, lalu jalankan:
   ```powershell
   java -jar simple-apps-backend-0.0.1-SNAPSHOT.jar
   ```
3. Tunggu log `Started SimpleAppsApplication`, lalu buka `http://localhost:8080`.
4. Selesaikan empat gerbang sesuai README aplikasi: Lisensi → .env (contoh durasi JWT `60`) → Cloudinary (butuh kredensial asli + internet) → Login.
5. Untuk tes Web Selenium/Cucumber, selesaikan gerbang melalui browser biasa terlebih dahulu. Gerbang konfigurasi bersifat server-side; setelah selesai, test browser automation bisa mengakses halaman login.
6. Akun seed: `admin1 / Admin1@123`, `admin2 / Admin2@123`, `customer1 / Customer1@123`, `sortir1 / Sortir1@123`, `csuser1 / Csuser1@123`.

**Catatan:** Aplikasi menggunakan H2 in-memory. Data akan reset saat backend dihentikan/restart. API dan Web berbagi backend/port yang sama.

## 2. Konfigurasi automation
Nilai konfigurasi terpusat di `src/test/java/id/co/juaracoding/util/TestConfig.java`.

Default:
- Base URL: `http://localhost:8080`
- X-API-KEY: license key yang disediakan di README Simple Apps
- Login: `admin1 / Admin1@123`

Override melalui Maven properties, contoh PowerShell:
```powershell
mvn clean test "-Dbase.url=http://localhost:8080" "-Dapi.key=d461265dd7323fef9755bb3257275d67" "-Dadmin.username=admin1" "-Dadmin.password=Admin1@123"
```
Untuk headless browser:
```powershell
mvn test "-Dheadless=true"
```
Untuk Web forgot-password, isi `-Dregistered.email=email-yang-benar-benar-terdaftar`. Jangan gunakan email contoh yang belum terdaftar.

## 3. Jalankan test
Dari root repository:
```powershell
mvn clean test
```
TestNG suite mencakup TestNG foundation, Selenium Web UI, RestAssured API, Cucumber Web, dan Cucumber API.

Hasil Surefire tersedia di `target/surefire-reports/`. Laporan Cucumber HTML:
- `target/cucumber-web.html`
- `target/cucumber-api.html`

Jika hanya ingin mengecek fondasi TestNG, jalankan:
```powershell
mvn -Dtest=TextUtilTest test
```

## 4. Catatan penting pengujian
- API selalu mengirim header `X-API-KEY` pada request `/api/v1/**`.
- Login API hanya mengenkripsi `password` dengan `RsaHelper.encrypt(...)`.
- Registrasi API mengenkripsi `password`, `email`, `birthDate`, `phoneNumber`, `idCardNumber`, dan `taxIdNumber`. Field lainnya tetap plaintext.
- Forgot Password API mengirim `email` plaintext; tidak menggunakan RSA encryption.
- Ciphertext RSA-OAEP berubah setiap pemanggilan. Jangan hardcode ciphertext; panggil helper pada setiap request.
- API test mengambil captcha baru melalui `GET /api/v1/captcha` dan menggunakan `captchaValue` serta `captchaHash` dari response.
- UI test membaca captcha testing hint melalui `data-testid` yang disediakan aplikasi dalam mode TESTING.
- Email aplikasi ditangkap Mailpit jika Mailpit dipasang dan dijalankan. Tanpa Mailpit, alur aplikasi tetap dapat berjalan tetapi email tidak dapat dilihat.
- UI selectors memakai `data-testid` dan ID yang ditemukan pada bundle UI Simple Apps. Jika versi aplikasi berubah, periksa kembali locator.
- Hasil test belum dinyatakan PASS sebelum dijalankan pada komputer lokal dengan aplikasi sudah melewati semua gerbang dan kredensial Cloudinary yang valid.

## 5. Mobile APK (persiapan manual)
APK yang disediakan adalah Flutter Android. Jalankan backend yang sama terlebih dahulu. Install ke LDPlayer/perangkat dengan Android Platform Tools:
```powershell
adb connect 127.0.0.1:5555
adb devices
adb install -r app-release.apk
```
Di layar pengaturan koneksi, isi Host dengan IP PC yang bisa dijangkau emulator (bukan `localhost`), Port `8080`, lalu gunakan license key yang sama. Pastikan Windows Firewall mengizinkan port backend. README paket aplikasi menjelaskan detail koneksi Mobile.

**Batas deliverable:** Dokumen praktikum yang tersedia menetapkan Selenium Web + RestAssured API dan Cucumber untuk membungkus kedua layer tersebut. APK dan langkah koneksinya disertakan sebagai target mobile, tetapi suite Appium belum ditambahkan karena tidak ada spesifikasi praktikum Appium/locator mobile pada dokumen yang diberikan.
