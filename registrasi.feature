Feature: Registrasi API
  Scenario: Registrasi API berhasil membuat akun baru
    Given API client siap digunakan
    When client membuat akun baru melalui API
    Then response API menunjukkan status sukses
