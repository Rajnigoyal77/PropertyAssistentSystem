package propertyfinder;

public class propertybean
{

    int rid;
    String mobile;
    String location;
    float size;

    public propertybean(int rid, String mobile, String location, float size, float front, float rear, float leftv, float rightv, String facing, String approvedby, int price) {
        this.rid = rid;
        this.mobile = mobile;
        this.location = location;
        this.size = size;
        this.front = front;
        this.rear = rear;
        this.leftv = leftv;
        this.rightv = rightv;
        this.facing = facing;
        this.approvedby = approvedby;
        this.price = price;
    }

    float front;
    float rear;
    float leftv;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        this.facing = facing;
    }

    public float getRightv() {
        return rightv;
    }

    public void setRightv(float rightv) {
        this.rightv = rightv;
    }

    public float getLeftv() {
        return leftv;
    }

    public void setLeftv(float leftv) {
        this.leftv = leftv;
    }

    public float getRear() {
        return rear;
    }

    public void setRear(float rear) {
        this.rear = rear;
    }

    public float getFront() {
        return front;
    }

    public void setFront(float front) {
        this.front = front;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    float rightv;
    String facing;
    String approvedby;
    int price;





}
