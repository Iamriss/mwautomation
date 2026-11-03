Feature: Lupa Password API
  Scenario: Lupa password berhasil untuk email akun yang baru dibuat
    Given API client siap digunakan
    When client meminta reset password untuk email akun baru
    Then response API menunjukkan status sukses
