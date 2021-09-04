import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.*;

public class Main {
    // Variables to export to pdf
    private static String FILE = System.getProperty("user.home") + "\\Desktop\\HomeBudget.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);

    public static void main(String[] args) {

        // Variables declaration
        BudgetList myHomeBudget = new BudgetList();
        PurchaseCategoryList purchaseList = new PurchaseCategoryList();
        Scanner scan = new Scanner(System.in);
        Date date = new Date();

        // User input
        System.out.println("Please input a name for your budget:");
        String budgetName = scan.nextLine();
        myHomeBudget.setName(budgetName);

        System.out.println("Please input your income:");
        double totalMount = numericInputValidation();

        // Default Budget objects creation
        double percentage = (30 * totalMount) / 100;
        Budget groceries = new Budget("Groceries", percentage); // 30% of total budget

        percentage = (10 * totalMount) / 100;
        Budget fuelMonthly = new Budget("Fuel", percentage); // 10% of total budget

        Budget personalSquandering = new Budget("Personal squandering", percentage);  // 10% of total budget

        percentage = (50 * totalMount) / 100;
        Budget bills = new Budget("Bills and predicted monthly expenses", percentage);  // 50% of total budget

        // Adding budgets to the myHomeBudget list
        myHomeBudget.addNewBudget(bills);
        myHomeBudget.addNewBudget(personalSquandering);
        myHomeBudget.addNewBudget(groceries);
        myHomeBudget.addNewBudget(fuelMonthly);

        // Default Purchase categories creation
        PurchaseCategory groceriesPurchases = new PurchaseCategory("Groceries");
        PurchaseCategory fuel = new PurchaseCategory("Fuel");
        PurchaseCategory eatingOut = new PurchaseCategory("Eating out");
        PurchaseCategory squandering = new PurchaseCategory("Squandering");
        PurchaseCategory occasionalSignificantPurchases = new PurchaseCategory("Occasional significant purchases");
        PurchaseCategory billsMonthly = new PurchaseCategory("Bills and savings");

        // Adding purchase categories to the PurchaseCategoryList
        purchaseList.addNewPurchaseCategory(fuel);
        purchaseList.addNewPurchaseCategory(eatingOut);
        purchaseList.addNewPurchaseCategory(squandering);
        purchaseList.addNewPurchaseCategory(occasionalSignificantPurchases);
        purchaseList.addNewPurchaseCategory(billsMonthly);
        purchaseList.addNewPurchaseCategory(groceriesPurchases);

        // Creating a savings program
        percentage = (10 * totalMount) / 100;
        Purchase depositToSavings = new Purchase("Deposit to saving account", percentage, false);
        bills.addPurchase(percentage);
        billsMonthly.addPurchaseToCategory(depositToSavings, percentage);
        System.out.println("\r\n10%, " + percentage + ", were added to Bills and predicted monthly expenses budget, under Bills and savings purchase category.\r\n");

        while (true){
            System.out.println("[P] - Print     [Pp] - Print purchases     [T] - total expenses     [Ec] - Expected credit Expenses     [Tp] - Total expenses by purchase category\r\n" +
                    "[Tl] - Total left in budget     [A] - Add a purchase/ bill     [R] - Remove purchase/ bill     [Ed] - Edit     [Pdf] - Export to pdf     [E] - Exit");
            String userInput = scan.nextLine();
            userInput.toLowerCase();
            String stopper = "y"; // Variable used to stop while loops
            switch (userInput) {
                case "p": //[P] - Print
                    clearScreen();
                    myHomeBudget.printList();
                    break;
                case "pp": //[Pp] - Print purchases
                    clearScreen();
                    purchaseList.printList();
                    break;
                case "t":  //[T] - Total expenses
                    clearScreen();
                    System.out.println("Total expenses up to " + date);
                    System.out.println(purchaseList.totalExpenses());
                    break;
                case "ec":   //[Ec] - Expected credit Expenses
                    clearScreen();
                    System.out.println("Expected credit expenses up to " + date);
                    System.out.println(purchaseList.totalCreditExpenses());
                    break;
                case "tp": //[Tp] - Total expenses by purchase category.
                    clearScreen();
                    purchaseList.printTotalExpensesByCategory();
                    break;
                case "tl": //[Tl] - Total left in budget
                    clearScreen();
                    myHomeBudget.printTotalLEft();
                    break;
                case "a":  //[A] - Add a purchase/ bill
                    clearScreen();
                    addPurchase(myHomeBudget, purchaseList);
                    break;
                case "r": //[R] - Remove purchase/ bill
                    clearScreen();
                    removePurchase(myHomeBudget, purchaseList);
                    break;
                case "ed":    // [Ed] - Edit
                    clearScreen();
                    editBudget(myHomeBudget);
                    break;
                case "pdf": // [Pdf] - Export to pdf
                clearScreen();
                createPdf(myHomeBudget,purchaseList);
                break;
                case "e":  //[E] - Exit
                    clearScreen();
                    System.exit(0)  ;
                default:
                    System.out.println("Invalid input");
            }
        }


    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void createPdf(BudgetList myHomeBudget, PurchaseCategoryList purchaseList){
        try {
            Date date = new Date();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document, myHomeBudget.getBudgetName());
            addContent(document, "Budgets:");
            addContent(document, " ");
            for (int i = 0; i < myHomeBudget.getList().size(); i++){
                addBudgetContent(document, myHomeBudget.getList().get(i).getName(), myHomeBudget.getList().get(i).getInitialValue(), myHomeBudget.getList().get(i).getTotalLeft());

            }
            addContent(document, " ");
            addContent(document, "Purchase categories:");
            addContent(document, " ");
            for (int i = 0; i < purchaseList.getList().size(); i++){
                addPurchaseList(document, purchaseList.getList().get(i).getPurchaseCategoryName(), purchaseList.getList().get(i).getTotalExpensesInCategory());
                for (int j = 0; j < purchaseList.getList().get(i).getPurchasesList().size(); j++ ){
                    addPurchaseContent(document, purchaseList.getList().get(i).getPurchasesList().get(j).getPurchaseName(), purchaseList.getList().get(i).getPurchasesList().get(j).getPurchaseAmount(), purchaseList.getList().get(i).getPurchasesList().get(j).getCreditPayIndicator());
                }
                addContent(document, "-------------------------------------------");
            }
            addContent(document, " ");
            addContent(document, "Useful information:");
            addContent(document, " ");
            addContent(document, "Total expenses:\r\n " +  purchaseList.totalExpenses());
            addContent(document, "Expected credit Expenses:\r\n " +  purchaseList.totalCreditExpenses());
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("pdf file created in directory:\r\n" + System.getProperty("user.home") + "\\Desktop");
    }

    // iText allows adding metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private static void addMetaData(Document document) {
        document.addTitle("Home Budget");
        document.addCreator("Report generated by: " + System.getProperty("user.name") + ", " + new Date());
        // document.addSubject("");
        // document.addKeywords("");
        // document.addAuthor("");

    }

    private static void addTitlePage(Document document, String title)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph(title, catFont));

        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                smallBold));
        addEmptyLine(preface, 1);
        document.add(preface);
        // Start a new page
        // document.newPage();
    }

    private static void addContent(Document document, String content) throws DocumentException{
        Paragraph par = new Paragraph(content, subFont);
        document.add(par);
    }

    private static void addSmallContent(Document document, String content) throws DocumentException{
        Paragraph par = new Paragraph(content, smallBold);
        document.add(par);
    }

    private static void addBudgetContent(Document document, String budgetName, double initialValue, double totalLeft) throws DocumentException {

        Paragraph title = new Paragraph(budgetName, subFont);
        Paragraph budgetDetail = new Paragraph(("Budget initial value: " + initialValue + "\r\n" +
                "Total left in Budget: " + totalLeft + "\r\n"));
        Paragraph line = new Paragraph("-------------------------------------------");
        // now add all this to the document
        document.add(title);
        document.add(budgetDetail);
        document.add(line);
    }

    private static void  addPurchaseList(Document document, String purchaseCategoryName, double totalExpensesInCategory) throws DocumentException{
        Paragraph title = new Paragraph(purchaseCategoryName, subFont);
        Paragraph categoryDetail = new Paragraph(("Total expenses in category: " + totalExpensesInCategory));

        // now add all this to the document
        document.add(title);
        document.add(categoryDetail);
    }

    private static void addPurchaseContent(Document document, String purchaseName, double purchaseAmount, boolean cpi) throws DocumentException{
        Paragraph purchaseDetail = new Paragraph();
        if (cpi)
            purchaseDetail = new Paragraph((purchaseName + " " + purchaseAmount + " Nis - via credit"));
        else
            purchaseDetail = new Paragraph((purchaseName + " " + purchaseAmount +" Nis"));

        document.add(purchaseDetail);
    }


    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static double simpleNumericInputValidation(){
        double input;
        Scanner scan = new Scanner(System.in);
        while (!scan.hasNextDouble()){
            System.out.println("Notice - Please enter numbers only.");
            scan.next();
        }
        input = scan.nextDouble();
        scan.nextLine();
        //System.out.println("Your input: " + input + ".\r\nPress Enter to continue.");
        return input;
    }

    private static double numericInputValidation(){
        double input;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("Notice - input must be a positive number.");
            while (!scan.hasNextDouble()){
                System.out.println("Notice - Please enter numbers only.");
                scan.nextLine();
            }
            input = scan.nextDouble();
            scan.nextLine();
        } while(input <= 0);
        //System.out.println("Your input: " + input + ".\r\nPress Enter to continue.");
        return input;
    }

    private static int budgetIndexInputValidation(BudgetList myHomeBudget){
        int userInput;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("Notice - choice must be out of available indexes.");
            while (!scan.hasNextInt()){
                System.out.println("Notice - Please enter numbers only.");
                scan.next();
            }
            userInput = scan.nextInt();
        } while( userInput < 0 || userInput >= myHomeBudget.getList().size());
        return userInput;
    }

    private static int categoryIndexInputValidation(PurchaseCategoryList purchaseList){
        int userInput;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("Notice - choice must be out of available indexes.");
            while (!scan.hasNextInt()){
                System.out.println("Notice - Please enter numbers only.");
                scan.next();
            }
            userInput = scan.nextInt();
        } while( userInput < 0 || userInput >= purchaseList.getList().size());
        return userInput;
    }

    private static int purchaseIndexInputValidation(PurchaseCategory category){
        int userInput;
        Scanner scan = new Scanner(System.in);
        do{
            System.out.println("Notice - choice must be out of available indexes.");
            while (!scan.hasNextInt()){
                System.out.println("Notice - Please enter numbers only.");
                scan.next();
            }
            userInput = scan.nextInt();
        } while( userInput < 0 || userInput >= category.getPurchasesList().size());
        return userInput;
    }

    private static void addPurchase(BudgetList myHomeBudget, PurchaseCategoryList purchaseList){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter purchase/bill description:");
        String description = scan.nextLine();

        System.out.println("Enter purchase/bill Amount");
        double purchaseAmount = numericInputValidation();
        scan.nextLine();

        System.out.println("Purchase/bill paid with credit?     [Y] - yes   [N] - No");
        String cpi = scan.nextLine();
        boolean creditIndicator;
        if (cpi.equalsIgnoreCase("y"))
            creditIndicator = true;
        else
            creditIndicator = false;

        Purchase purchase = new Purchase(description, purchaseAmount, creditIndicator);

        System.out.println("Enter the number of the corresponding budget:");
        myHomeBudget.indexListPrint();
        int budgetIndex = budgetIndexInputValidation(myHomeBudget);
        myHomeBudget.getList().get(budgetIndex).addPurchase(purchaseAmount);

        System.out.println("Enter the number of the corresponding category:");
        purchaseList.indexListPrint();
        int listIndex = categoryIndexInputValidation(purchaseList);
        purchaseList.getList().get(listIndex).addPurchaseToCategory(purchase, purchaseAmount);
    }

    private static void removePurchase(BudgetList myHomeBudget, PurchaseCategoryList purchaseList){
        System.out.println("Enter the number of the corresponding budget:");
        myHomeBudget.indexListPrint();
        int budgetIndex = budgetIndexInputValidation(myHomeBudget);

        System.out.println("Enter the number of the corresponding category:");
        purchaseList.indexListPrint();
        int listIndex = categoryIndexInputValidation(purchaseList);

        if (purchaseList.getList().get(listIndex).getTotalExpensesInCategory() == 0){
            System.out.println("No purchases in this category");
            return;
        }
        else{
            System.out.println("Enter the number of the corresponding purchase/bill:");
            purchaseList.getList().get(listIndex).indexPurchasePrint();
            int purchaseIndex = purchaseIndexInputValidation(purchaseList.getList().get(listIndex));

            myHomeBudget.getList().get(budgetIndex).removePurchase(purchaseList.getList().get(listIndex).getPurchaseFromList(purchaseIndex).getPurchaseAmount());
            purchaseList.getList().get(listIndex).removePurchaseFromCategory(purchaseList.getList().get(listIndex).getPurchaseFromList(purchaseIndex).getPurchaseAmount(), purchaseIndex);
            System.out.println("Purchase removed");
        }
    }

    private static void editBudget(BudgetList myHomeBudget){
        Scanner scan = new Scanner(System.in);
        System.out.println("What budget would you like to edit?\r\n" + "Enter the number of the corresponding budget:");
        myHomeBudget.indexListPrint();
        int budgetIndex = budgetIndexInputValidation(myHomeBudget);

        System.out.println("[N] - Change budget name     [A] - Change budget initial amount     [T] - Change the amount left in budget");

        while (true){
            String userInput = scan.nextLine();
            switch (userInput) {
                case "n": // [N] - Change budget name
                    System.out.println("Current budget name: " + myHomeBudget.getList().get(budgetIndex).getName());
                    System.out.println("Input new budget name: ");
                    String name = scan.nextLine();
                    myHomeBudget.getList().get(budgetIndex).setName(name);
                    System.out.println("Budget name changed to " + name);
                    return;
                case "a": // [A] - Change budget initial amount
                    System.out.println("Current initial budget amount: " + myHomeBudget.getList().get(budgetIndex).getInitialValue());
                    System.out.println("Input new initial value: ");
                    double value = numericInputValidation();
                    if (value > myHomeBudget.getList().get(budgetIndex).getInitialValue()){
                        double temp = value - myHomeBudget.getList().get(budgetIndex).getInitialValue();
                        myHomeBudget.getList().get(budgetIndex).setInitialValue(value);
                        myHomeBudget.getList().get(budgetIndex).setTotalLeftValue(myHomeBudget.getList().get(budgetIndex).getTotalLeft() + temp);
                    }
                    else if ((value < myHomeBudget.getList().get(budgetIndex).getInitialValue()) && (value > myHomeBudget.getList().get(budgetIndex).getTotalLeft())){
                        myHomeBudget.getList().get(budgetIndex).setInitialValue(value);
                    }
                    else{
                        myHomeBudget.getList().get(budgetIndex).setInitialValue(value);
                        myHomeBudget.getList().get(budgetIndex).setTotalLeftValue(value);
                    }
                    System.out.println("Budget initial value changed to " + value);
                    System.out.println("Total left in budget: " + myHomeBudget.getList().get(budgetIndex).getTotalLeft());
                    return;
                case "t": // [T] - Change the amount left in budget
                    System.out.println("Current amount left in budget: " + myHomeBudget.getList().get(budgetIndex).getTotalLeft());
                    System.out.println("Input new value: ");
                    double totalLeft = simpleNumericInputValidation();
                    myHomeBudget.getList().get(budgetIndex).setTotalLeftValue(totalLeft);
                    System.out.println("Amount left in budget changed to: " + totalLeft);
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }












}
