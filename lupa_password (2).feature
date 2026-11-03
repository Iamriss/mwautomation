Feature: Lupa Password Web
  Scenario: Permintaan lupa password menggunakan email terdaftar
    Given user berada di halaman lupa password web
    When user mengirim email terdaftar pada form lupa password web
    Then aplikasi web memberikan respons lupa password
