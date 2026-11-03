Feature: Login Web
  Scenario: Login berhasil dengan kredensial valid
    Given user berada di halaman login web
    When user login web dengan kredensial valid
    Then user web berhasil masuk
