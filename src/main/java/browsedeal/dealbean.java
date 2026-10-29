package browsedeal;

public class dealbean
{

    int id;
    String seller;
    String sellercontact;
    String buyer;
    String buyercontact;
    int finalamt;
    int downpmt;
    String dtofdeal;
    String totalcom;
    int adv;
    int balance;


    public dealbean(int id, String seller, String sellercontact, String buyer,
                    String buyercontact, int finalamt, int downpmt,
                    String dtofdeal, String totalcom,
                    int adv, int balance)
    {
        this.id = id;
        this.seller = seller;
        this.sellercontact = sellercontact;
        this.buyer = buyer;
        this.buyercontact = buyercontact;
        this.finalamt = finalamt;
        this.downpmt = downpmt;
        this.dtofdeal = dtofdeal;
        this.totalcom = totalcom;
        this.adv = adv;
        this.balance = balance;
    }


    public int getId() {
        return id;
    }

    public String getSeller() {
        return seller;
    }

    public String getSellercontact() {
        return sellercontact;
    }

    public String getBuyer() {
        return buyer;
    }

    public String getBuyercontact() {
        return buyercontact;
    }

    public int getFinalamt() {
        return finalamt;
    }

    public int getDownpmt() {
        return downpmt;
    }

    public String getDtofdeal() {
        return dtofdeal;
    }

    public String getTotalcom() {
        return totalcom;
    }

    public int getAdv() {
        return adv;
    }

    public int getBalance() {
        return balance;
    }

}