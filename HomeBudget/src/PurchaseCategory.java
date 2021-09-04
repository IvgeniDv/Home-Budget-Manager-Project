import java.util.*;

public class PurchaseCategory {
    // Var declaration
    protected String _purchaseCategoryName;
    protected double _totalExpensesInCategory;
    protected ArrayList<Purchase> purchasesList;

    // Constructors
    public PurchaseCategory(){
        _purchaseCategoryName = "New Category";
        _totalExpensesInCategory = 0;
        purchasesList = new ArrayList<Purchase>();
    }

    public PurchaseCategory(String name){
        _purchaseCategoryName = name;
        _totalExpensesInCategory = 0;
        purchasesList = new ArrayList<Purchase>();
    }

    // Getters
    protected Purchase getPurchaseFromList(int index){
        return this.purchasesList.get(index);
    }

    protected ArrayList<Purchase> getPurchasesList(){
        return purchasesList;
    }

    protected String getPurchaseCategoryName(){
        return _purchaseCategoryName;
    }

    protected double getTotalExpensesInCategory(){
        return _totalExpensesInCategory;
    }

    protected  double getTotalCreditExpenses(){
        double total = 0;
        for(int i = 0; i < purchasesList.size(); i++) {
            if (purchasesList.get(i).getCreditPayIndicator())
                total += purchasesList.get(i).getPurchaseAmount();
        }
        return total;
    }

    protected void printPurchaseList(){
        System.out.println("Purchase category: " + _purchaseCategoryName);
        System.out.println("Total Expenses In Category " + _totalExpensesInCategory);
        for (int i = 0; i < purchasesList.size(); i++){
            purchasesList.get(i).printPurchase();
        }
    }

    protected void indexPurchasePrint(){
        for (int i = 0; i < purchasesList.size(); i++){
            System.out.print("[" + i + "] ");
            purchasesList.get(i).printPurchase();
        }
    }

    // Setters
    protected void addPurchaseToCategory(Purchase newPurchase, double purchaseAmount){
        this.setTotalExpensesInCategory(purchaseAmount);
        purchasesList.add(newPurchase);
    }

    protected  void removePurchaseFromCategory(double purchaseAmount, int purchaseIndex){
        purchaseAmount = purchaseAmount * (-1);
        this.setTotalExpensesInCategory(purchaseAmount);
        purchasesList.remove(purchaseIndex);
    }

    protected void  setTotalExpensesInCategory(double purchase){
        _totalExpensesInCategory = _totalExpensesInCategory + purchase;
    }





}
