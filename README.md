# mwautomation

Project Praktikum Berseri #1 – TestNG.

## Struktur
```text
mwautomation/
├── pom.xml
├── testng.xml
└── src/
    ├── main/java/id/co/juaracoding/util/
    │   └── TextUtil.java
    └── test/java/id/co/juaracoding/testng/
        └── TextUtilTest.java
```

## Menjalankan test
Pastikan JDK dan Maven sudah terpasang, lalu jalankan:

```bash
mvn clean test
```

Test mencakup:
- `isPalindrome`: skenario palindrome dan non-palindrome.
- `countVowels`: penghitungan vokal dan input null.
- Lifecycle: `@BeforeMethod`.
