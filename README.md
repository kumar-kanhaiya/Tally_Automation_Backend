# Tally Automation Backend

A Spring Boot–based backend system to automate data exchange with **Tally Prime** using its XML over HTTP interface.  
This project focuses on **programmatic generation and import of accounting data** (Companies, Ledgers, Vouchers) into Tally Prime, eliminating manual entry and reducing accounting errors.

---

## 🚀 Features

- Generate **Tally-compliant XML** for:
  - Companies
  - Ledgers
  - Vouchers
- Supported Voucher Types:
  - Contra (Deposit)
  - Contra (Withdraw)
  - Payment
  - Receipt
- Handles **Debit / Credit logic** correctly using `ISDEEMEDPOSITIVE`
- XML sanitization to handle **invalid Tally characters** (e.g. `&#4;`)
- Clean layered architecture:
  - Controller Layer
  - Service Layer
  - Parser / XML Generator Layer
- Easily extensible for future connectors (ERP, Web Apps, Fintech systems)

---

## 🧠 Architecture Overview


Controller
↓
Service
↓
Parser / XML Generator
↓
Tally Prime (HTTP XML API)


- **Controller**: Exposes REST endpoints
- **Service**: Handles business logic
- **Parser / Generator**: Builds or parses Tally XML
- **Tally Prime**: Runs locally and consumes XML

---

## 🛠 Tech Stack

- Java 17+
- Spring Boot
- XML (Tally Prime format)
- REST APIs
- JUnit 5 (Testing)

---

## 📂 Project Structure

src/main/java/com/tallybackend/tally_backend
│
├── controller
│ ├── CompanyController.java
│ ├── LedgerController.java
│ └── VoucherController.java
│
├── service
│ ├── CompanyService.java
│ ├── LedgerService.java
│ ├── VoucherXmlService.java
│ └── parser
│ ├── CompanyParserService.java
│ ├── LedgerParserService.java
│ └── VoucherParserService.java
│
├── util
│ └── XmlSanitizer.java
│
└── TallyBackendApplication.java


---

## 🔄 Voucher XML Flow

1. Frontend sends voucher data in JSON format
2. Backend converts JSON → Tally XML
3. XML is wrapped inside Tally `ENVELOPE`
4. XML is sent to Tally Prime (localhost)
5. Tally returns success / failure response

---

## 🧾 Sample Voucher JSON

```json
{
  "voucherType": "CONTRA_DEPOSIT",
  "date": "20250601",
  "narration": "Cash deposited into bank",
  "entries": [
    { "ledger": "CANARA BANK", "amount": 15000 },
    { "ledger": "Cash", "amount": -15000 }
  ]
}

🧾 Generated Tally XML (Simplified)
<VOUCHER VCHTYPE="Contra" ACTION="Create">
    <DATE>20250601</DATE>
    <VOUCHERTYPENAME>Contra</VOUCHERTYPENAME>
    <NARRATION>Cash deposited into bank</NARRATION>

    <ALLLEDGERENTRIES.LIST>
        <LEDGERNAME>CANARA BANK</LEDGERNAME>
        <ISDEEMEDPOSITIVE>No</ISDEEMEDPOSITIVE>
        <AMOUNT>15000.00</AMOUNT>
    </ALLLEDGERENTRIES.LIST>

    <ALLLEDGERENTRIES.LIST>
        <LEDGERNAME>Cash</LEDGERNAME>
        <ISDEEMEDPOSITIVE>Yes</ISDEEMEDPOSITIVE>
        <AMOUNT>-15000.00</AMOUNT>
    </ALLLEDGERENTRIES.LIST>
</VOUCHER>

🧪 Testing

JUnit test example for voucher XML generation:

@SpringBootTest
class VoucherTesting {

    @Autowired
    private VoucherXmlService voucherXmlService;

    @Test
    void xmlServiceTest() {
        String xml = voucherXmlService.generateVoucherXml(...);
        assertNotNull(xml);
        assertTrue(xml.contains("<VOUCHER"));
    }
}

⚙️ Prerequisites

Java 17+

Maven

Tally Prime (running locally)

Tally HTTP XML enabled (default port: 9000)

▶️ Running the Project
mvn clean install
mvn spring-boot:run

🎯 Use Cases

Automating accounting entries

ERP ↔ Tally integration

Fintech & invoicing platforms

Eliminating manual voucher entry

Real-time accounting automation

🔮 Future Enhancements

Authentication & user management

Direct HTTP connector to Tally

Voucher status tracking

Error handling & retry mechanism

Multi-company support

👨‍💻 Author

Kanhaiya Kumar
Aspiring Software Developer | Backend Engineer
GitHub: https://github.com/kumar-kanhaiya
Anuj Kumar
Aspiring Backend Developer 
Github: https://github.com/CraftyScripter

