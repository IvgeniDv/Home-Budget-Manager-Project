import java.util.*;

public class PurchaseCategoryList {
    // Var declaration
    ArrayList<PurchaseCategory> purchaseCategoryList;

    // Constructors
    public PurchaseCategoryList(){
        purchaseCategoryList = new ArrayList<PurchaseCategory>();
    }

    // Setters
    protected void addNewPurchaseCategory(PurchaseCategory purchaseCategory){
        purchaseCategoryList.add(purchaseCategory);
    }

    // Getters
    protected ArrayList<PurchaseCategory> getList(){
        return purchaseCategoryList;
    }

    protected void printList(){
        for(int i = 0; i < purchaseCategoryList.size(); i++){
            purchaseCategoryList.get(i).printPurchaseList();
            System.out.println("**********");
        }
    }

    protected void indexListPrint(){
        for (int i = 0; i < purchaseCategoryList.size(); i++){
            System.out.println("[" + i + "]" + purchaseCategoryList.get(i).getPurchaseCategoryName());
        }
    }

    protected void printTotalExpensesByCategory(){
        for (int i = 0; i < purchaseCategoryList.size(); i++){
            System.out.println("Purchase category: " + purchaseCategoryList.get(i).getPurchaseCategoryName());
            System.out.println("Total expenses in category: " +purchaseCategoryList.get(i).getTotalExpensesInCategory());
        }
    }

    protected double totalExpenses(){
        double total = 0;
        for(int i = 0; i < purchaseCategoryList.size(); i++){
            total += purchaseCategoryList.get(i).getTotalExpensesInCategory();
        }
        return total;
    }

    protected double totalCreditExpenses(){
        double total = 0;
        for(int i = 0; i < purchaseCategoryList.size(); i++) {
            total += purchaseCategoryList.get(i).getTotalCreditExpenses();
        }
        return total;
    }



}
