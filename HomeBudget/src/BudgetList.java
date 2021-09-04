import java.util.*;

public class BudgetList {
    // Var declaration
    protected String _budgetName;
    protected ArrayList<Budget> myHomeBudget;

    // Constructors
    public BudgetList(){
        _budgetName = "New Budget";
        myHomeBudget = new ArrayList<Budget>();
    }

    protected void addNewBudget(Budget budget){
        myHomeBudget.add(budget);
    }

    // Getters
    protected  String getBudgetName(){
        return _budgetName;
    }

    protected ArrayList<Budget> getList(){
        return myHomeBudget;
    }

    protected void printList(){
        System.out.println(_budgetName );
        for(int i = 0; i < myHomeBudget.size(); i++){
            myHomeBudget.get(i).printMyBudget();
            System.out.println("**********");
        }
    }

    protected void printTotalLEft(){
        for(int i = 0; i < myHomeBudget.size(); i++){
            System.out.println("Budget name: " + myHomeBudget.get(i).getName());
            System.out.println("Total left in budget: " + myHomeBudget.get(i). getTotalLeft() + " Nis");

        }
    }

    protected void indexListPrint(){
        for (int i = 0; i < myHomeBudget.size(); i++){
            System.out.println("[" + i + "]" + myHomeBudget.get(i).getName());
        }
    }

    protected double predictedExpenses(){
        double total = 0;
        for(int i = 0; i < myHomeBudget.size(); i++) {
            if (myHomeBudget.get(i).getTotalLeft() < 0) {
                total += myHomeBudget.get(i).getInitialValue() + Math.abs(myHomeBudget.get(i).getTotalLeft());
            }
            else {
                total += myHomeBudget.get(i).getInitialValue();
            }
        }
        return total;
    }

    // Setters
    protected void setName(String name){
        _budgetName = name;
    }




}
