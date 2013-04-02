package com.example.expensiv.db;

import java.util.ArrayList;

public class SQLStatements {
	static String CREATE_BANK = "CREATE TABLE IF NOT EXISTS " + MySqlLiteHelper.TABLE_BANK + 
			"    ("+
			"    "+ MySqlLiteHelper.BANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
			"    "+ MySqlLiteHelper.BANK_NAME + " VARCHAR(255) NOT NULL,"+
			"    "+ MySqlLiteHelper.BANK_SMS_CODE + " VARCHAR(20) NOT NULL "+
			"    )";
	
	static String DROP_BANK = "DROP TABLE IF EXISTS " + MySqlLiteHelper.TABLE_BANK ;
	
	
	//// BANK_SMS ////
	static String DROP_BANK_SMS = "DROP TABLE IF EXISTS BANK_SMS";
	
	static String CREATE_BANK_SMS = "CREATE TABLE IF NOT EXISTS BANK_SMS"+
			"    ("+
			"    ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
			"    SERVICE_PROVIDER VARCHAR(100) NOT NULL,"+
			"    TITLE VARCHAR(255) NOT NULL,"+
			"    PHONE_NO VARCHAR(255) NOT NULL,"+
			"    DESCRIPTION VARCHAR(255) NOT NULL,"+
			"    SMS_TEXT VARCHAR(160) NOT NULL,"+
			"    CREATED_BY VARCHAR(50) NOT NULL"+
			"    )";
	
	public ArrayList<String> getBankSMSInsertSQL(){
		ArrayList<String> INSERT_BANK_SMS_LIST = new ArrayList<String>();
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Prepaid Mobile Recharge',	'09222208888',	'If the last 6 digits of my account number are 123456, to recharge a Vodafone number 9811654321 for Rs. 100, I will send the following SMS to 9222208888: MTOPUP 9811654321 Vodafone 100 123456',	'MTOPUP XXXXXXXXXX Vodafone 100 123456',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'DTH Recharge',	'09222208888',	'If the last 6 digits of my account number are 123456, to recharge a TATASKY number 1005825110 for Rs. 100, I will send the following SMS to 9222208888:',	'DTH <1005825110> <TATASKY> <100> <123456>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Retrieve MMID',	'09222208888',	'Retrieve MMID',	'MMID <last 4 digits of your account number>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Balance Enquiry ',	'09215676766',	'Balance Enquiry -Primary account ',	'IBAL',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Last 3 transactions',	'09215676766',	'Last 3 transactions-Primary account ',	'ITRAN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Cheque Status Enquiry',	'09215676766',	'Cheque Status Enquiry-Primary account ',	'ICSI <Cheque No.>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Stop Cheque Request',	'09215676766',	'Stop Cheque Request-Primary account ',	'ISCR <Cheque No>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Cheque Book Request',	'09215676766',	'Cheque Book Request-Primary account ',	'ICBR',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'View Presented Bills',	'09215676766',	'View Presented Bills',	'IVIEW <Biller Nickname>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Balance Enquiry ',	'09215676766',	'Balance Enquiry -Non - Primary account ',	'IBAL <Last 6 digits of Account No>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Last 3 transactions',	'09215676766',	'Last 3 transactions-Primary account ',	'ITRAN <Last 6 digits of Account No>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Cheque Status Enquiry',	'09215676766',	'Cheque Status Enquiry-Primary account ',	'ICSI <Cheque No.> <Last 6 digits of Account No>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Stop Cheque Request',	'09215676766',	'Stop Cheque Request-Primary account ',	'ISCR <Cheque No> <Last 6 digits of Account No>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Cheque Book Request',	'09215676766',	'Cheque Book Request-Primary account ',	'ICBR <Last 6 digits of Account No>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Credit card Balance Enquiry',	'09215676766',	'Credit card Balance Enquiry',	'IBALCC Last 6 digits of Credit Card',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Credit card Reward Points',	'09215676766',	'Credit card Reward Points',	'IRPCC Last 6 digits of Credit Card',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Credit card Last Payment',	'09215676766',	'Credit card Last Payment',	'ILPCC Last 6 digits of Credit Card',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Credit card Payment Due Date',	'09215676766',	'Credit card Payment Due Date',	'IPDDCC Last 6 digits of Credit Card',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Demat Account Holding Enquiry',	'09215676766',	'Demat Account Holding Enquiry',	'IBALD   last 8 digits of account',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Demat Account Transaction Status',	'09215676766',	'Demat Account Transaction Status',	'ITRAND   please enter the document type and slip number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Demat Account Bill Enquiry',	'09215676766',	'Demat Account Bill Enquiry',	'IBILLD   last 8 digits of account',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Demat Account ISIN Enquiry',	'09215676766',	'Demat Account ISIN Enquiry',	'ISIND   please enter any scrip descriptor',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Amortisation Schedule',	'09215676766',	'Loan Account Amortisation Schedule',	'IAMT 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Income Tax Certificate Provisional',	'09215676766',	'Loan Account Income Tax Certificate Provisional',	'IITP 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Income Tax Certificate Final',	'09215676766',	'Loan Account Income Tax Certificate Final',	'IITF 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Reschedulement Letter',	'09215676766',	'Loan Account Reschedulement Letter',	'IRSL 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Reset Letter',	'09215676766',	'Loan Account Reset Letter',	'IRTL 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Loan Agreement Copy',	'09215676766',	'Loan Account Loan Agreement Copy',	'ILAC 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Interest Certificate',	'09215676766',	'Loan Account Interest Certificate',	'IINT 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Loan Account Welcome Letter',	'09215676766',	'Loan Account Welcome Letter',	'IWEL 16 Digits of Loan Account Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Status of Service Request Raised',	'09215676766',	'Status of Service Request Raised',	'SR Service Request Number',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Locate ATM',	'09215676766',	'Locate ATM',	'ATM PINCODE',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Locate BRANCH',	'09215676766',	'Locate BRANCH',	'BRANCH PINCODE',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'ICICI',	'Locate Bank@HomeDropBox',	'09215676766',	'Locate Bank@HomeDropBox',	'BHOME PINCODE',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Union',	'Account Balance',	'56677',	'Account Balance',	'UBAL',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Union',	'Last 3 transactions',	'56677',	'Last 3 transactions',	'UMNS',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Union',	'Status of Cheque Leaf',	'56677',	'Status of Cheque Leaf',	'UCSR',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Union',	'Help',	'56677',	'Help',	'UHELP',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Balance enquiry',	'09987123123',	'Balance enquiry',	'BAL',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Mini statement',	'09987123123',	'Mini statement',	'TXN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Cheque book request',	'09987123123',	'Cheque book request',	'CBR',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Statement request',	'09987123123',	'Statement request',	'STR',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Help',	'09987123123',	'Help',	'HELP',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Change pin',	'09987123123',	'Change pin',	'CPIN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Available credit limit',	'09987123123',	'Available credit limit',	'LIM <last 4 digits of Card Number>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Standard Chartered',	'Payment due date',	'09987123123',	'Payment due date',	'PDD <last 4 digits of card number>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Account Balance',	'09880752484',	'Account Balance where XXXX denotes last 4-digits of your Debit / Credit Card number',	'BAL XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Last Salary Credit in Bank Account',	'09880752484',	'Last Salary Credit in Bank Account where XXXX denotes last 4-digits of your Debit / Credit Card number',	'SAL XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Nearest ATM',	'09880752484',	'Nearest ATM',	'ATM <Location>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Duplicate Statement',	'09880752484',	'Duplicate Statement',	'DUPSTAT XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'New Chequebook',	'09880752484',	'New Chequebook',	'CHQBOOK XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Total Amount Due, Minimum Amount Due and Payment Due Date  ',	'09880752484',	'Total Amount Due, Minimum Amount Due and Payment Due Date where XXXX denotes last 4-digits of your Debit / Credit Card number',	'STMT XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Credit Card Outstanding with Available Credit Limit  ',	'09880752484',	'Credit Card Outstanding with Available Credit Limit where XXXX denotes last 4-digits of your Debit / Credit Card number',	'CARDBAL XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'Citibank',	'Reward Points  ',	'09880752484',	'Reward Points where XXXX denotes last 4-digits of your Debit / Credit Card number',	'REWARDS XXXX',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Balance Enquiry',	'5676712',	'Get the available balance in the accounts (up to 5) linked to your Customer ID. Your first five accounts according to chronological order will be considered.',	'bal',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Mini Statement',	'5676712',	'This will give the last three debits / credits made to your account.',	'txn',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Cheque Status Enquiry',	'5676712',	'Status of a cheque issued by you, whether paid / stopped.',	'cst <6 digit cheque no.>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Cheque Book Request',	'5676712',	'cheque book is mailed to you.',	'chq',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Stop Cheque',	'5676712',	'Stops an issued cheque from being paid.',	'stp <6 digit cheque no.>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Request for an Account Statement',	'5676712',	'Your account statement will be mailed to you (Period of the statement would be from the last statement received till the date of sending sms)',	'stm',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Bill Details',	'5676712',	'Your electricity and telephone bill details will be sent to you via SMS',	'bil',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Fixed Deposit Enquiry',	'5676712',	'The details of current fixed deposit with the bank (max. 5 accounts). You will see your account no., principal amount, rate of interest, maturity date and maturity amount.',	'fdq',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Change of Primary Account',	'5676712',	'The option to change your primary account and carry out transactions on the new account.',	'new <14 digit account no.>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'Internet Banking password (IPIN) regeneration',	'5676712',	'Get a confirmed receipt of your IPIN regeneration request. The IPIN is sent to your mailing address.',	'ipin',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'HDFC',	'List of keywords',	'5676712',	'Gives the list of Keywords and their Functions.',	'help',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Getting User id and Mpin',	'09223440000',	'Getting User id and Mpin',	'<Mbsreg> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Change Mpin',	'09223440000',	'Change Mpin',	'<Smpin><UserId><Old Mpin><New Mpin> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Accept Terms and Conditions of the Service, which are available over Bank’s website www.sbi.co.in.',	'09223440000',	'Accept Terms and Conditions of the Service, which are available over Bank’s website www.sbi.co.in.',	'<Saccept><UserId><Mpin> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Balance Enquiry',	'09223440000',	'Balance Enquiry',	'<Sbal><UserId><Mpin> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Mini statement of account',	'09223440000',	'Mini statement of account',	'<Smin><UserId><Mpin> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Mobile Top-up  ',	'09223440000',	'Mobile Top-up  ',	'<Stopup><UserId><Mpin><telecom operator of beneficiary Mobile><Mob no of beneficiary> <Amount>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Mobile Top-up  If the transaction requires an OTP, the following keyword should be sent:',	'09223440000',	'Mobile Top-up  If the transaction requires an OTP, the following keyword should be sent:',	'<OTP>< OTP number ><Stopup><UserId><Mpin><telecom operator of beneficiary Mobile><Mob no of beneficiary> <Amount>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'DTH recharge ',	'09223440000',	'DTH recharge ',	'<Sdth><UserId><Mpin><Service Provider><DTH serial number><amount>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'DTH recharge If the transaction requires an OTP, the following keyword should be sent:',	'09223440000',	'DTH recharge If the transaction requires an OTP, the following keyword should be sent:',	'<OTP>< OTP number ><Sdth><UserId><Mpin><Service Provider><DTH serial number><amount>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Top up of Mobile wallet',	'09223440000',	'Top up of Mobile wallet',	'<Smobitop><UserId><Mpin><WalletID><amount>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Top up of Mobile wallet If the transaction requires an OTP, the following keyword should be sent:',	'09223440000',	'Top up of Mobile wallet If the transaction requires an OTP, the following keyword should be sent:',	'<OTP>< OTP number > <Smobitop><UserId><Mpin><WalletID><amount>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'Forgot MPIN',	'09223440000',	'Forgot MPIN',	'<Sfpin> <User ID> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'To de register  from servicePlease Note: The keywords are not case sensitive.',	'09223440000',	'To de register  from servicePlease Note: The keywords are not case sensitive.',	'<Sdereg> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'IMPS fund transfer',	'09223440000',	'IMPS fund transfer',	'<IMPS><Mobile No><MMID><amount><User ID><MPIN><Purpose(optional field- up to 20 char-Alpha numeric)>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'SBI',	'IMPS fund transfer If the transaction requires an OTP, the following keyword should be sent:',	'09223440000',	'IMPS fund transfer If the transaction requires an OTP, the following keyword should be sent:',	'<OTP>< OTP number ><IMPS><Mobile No><MMID><amount><User ID><MPIN><Purpose(optional field- up to 20 char-Alpha numeric)>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'Balance Enquiry',	'09717000002',	'Gives you the balance for the specified account number. The account number is optional. If no account number is specified, you will get the balance in your primary account.',	'BAL [account-number]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'Last 3 transactions',	'09717000002',	'Gives you the last three transactions in the specified account. The account number is optional. If no account number is specified, then returns last three transactions in your primary account.',	'MINI [account-number]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'Cheque status',	'09717000002',	'Gives you the status of your requested cheque.',	'CHQSTATUS <account-number> <cheque-number>',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'Cheque Book Request',	'09717000002',	'This enables you to request Cheque Book for the specified account*.',	'CHQBK <account-number> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'Security balance in Demat account',	'09717000002',	'Gives the balance of your specified demat share/security under the specified client id.',	'DMAT <client-id> <ISIN-number> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'Activate SMS Banking',	'09717000002',	'Activates SMS Banking for registered but deactivated account.',	'ACT <full account-number> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'De-register from SMS Banking',	'09717000002',	'The specified account linked to the SMS number is de-registered.',	'SUSPEND <account-number> ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'AXIS',	'To know your SMS Banking registration date',	'09717000002',	'Gives the registration date/s of the linked account/s.',	'REGDATE ',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Balance enquiry',	'09820346920',	' Balance enquiry',	'BAL CUSTOMERID PIN [ACCOUNT NUMBER]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Fixed deposits enquiry',	'09820346920',	' Fixed deposits enquiry',	'FD CUSTOMERID PIN [ACCOUNT NUMBER]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Last 3 Transactions',	'09820346920',	' Last 3 Transactions',	'TXN CUSTOMERID PIN [ACCOUNT NUMBER]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Cheque Payment Status (to ascertain whether the cheque is paid or not)',	'09820346920',	' Cheque Payment Status (to ascertain whether the cheque is paid or not)',	'CPS CUSTOMERID PIN CHEQUE NUMBER [ACCOUNT NUMBER]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Request for cheque book',	'09820346920',	' Request for cheque book',	'CBR CUSTOMERID PIN NUMBER OF LEAVES P/M [ACCOUNT NUMBER]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Request for statement ',	'09820346920',	' Request for statement ',	'STM CUSTOMERID PIN FROM-DATE-TO-DATE [ACCOUNT NUMBER]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Change of PIN',	'09820346920',	' Change of PIN',	'CPN CUSTOMERID OLDPIN NEWPIN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Free balance Holdings  (Passing the customer ID)',	'09820346920',	' Free balance Holdings  (Passing the customer ID)',	'DB CUSTOMERID PIN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Last two Transactions',	'09820346920',	' Last two Transactions',	'DT CUSTOMERID PIN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Pending Bills',	'09820346920',	' Pending Bills',	'GETBILL CUSTOMERID PIN [PAYEEID]',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Pay Bill',	'09820346920',	' Pay Bill',	'PAYBILL CUSTOMERID PIN BILLID',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Registered Billers',	'09820346920',	' Registered Billers',	'MYBILLERS CUSTOMERID PIN',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	' Change Primary Account',	'09820346920',	' Change Primary Account',	'CPA CUSTOMERID PIN ACCOUNT NUMBER',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	insert into BANK_SMS	values(	NULL,	'IDBI',	'HELP',	'09820346920',	'HELP',	'HELP',	'shashank')	;");
		INSERT_BANK_SMS_LIST.add( "	update BANK_SMS set created_by = 'shashank' where created_by is null;");

    	return INSERT_BANK_SMS_LIST;

	}
    
    
    	
    
}
