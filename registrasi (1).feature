Feature: Registrasi Web
  Scenario: Registrasi berhasil dengan data valid
    Given user berada di halaman registrasi web
    When user mengirim form registrasi web yang valid
    Then aplikasi web memberikan respons registrasi
