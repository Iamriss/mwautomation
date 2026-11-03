Feature: Login API
  Scenario: Login API berhasil dengan kredensial valid
    Given API client siap digunakan
    When client mengirim login API dengan kredensial valid
    Then response API menunjukkan status sukses
