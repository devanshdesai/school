public class Car implements Comparable<Car>
{
    private String vin;
    private String make;
    private String model;
    private int price;
    private int mileage;
    private String color;
    private int priceOrMileage = 0;

    public Car(String vinArg, String makeArg, String modelArg, int priceArg, int mileageArg, String colorArg)
    {
        vin = vinArg;
        make = makeArg;
        model = modelArg;
        price = priceArg;
        mileage = mileageArg;
        color = colorArg;
    }

    public int compareTo(Car anotherCar)
    {
        if (priceOrMileage == 0)
        {
            if (anotherCar.getPrice() > this.getPrice())
                return -1;
            else if (anotherCar.getPrice() == this.getPrice())
                return 0;
            else
                return 1;
        }
        else
        {
            if (anotherCar.getMileage() > this.getMileage())
                return -1;
            else if (anotherCar.getMileage() == this.getMileage())
                return 0;
            else
                return 1;
        }
    }

    public Car cloneToMileage()
    {
        Car temp = new Car(vin, make, model, price, mileage, color);
        temp.setPriceOrMileage(1);
        return temp;
    }

    public Car cloneToPrice()
    {
        Car temp = new Car(vin, make, model, price, mileage, color);
        temp.setPriceOrMileage(0);
        return temp;
    }

    public void setPriceOrMileage(int pOrM)
    {
        priceOrMileage = pOrM;
    }

    public void printCar()
    {
        System.out.println(vin + "\n" + make + " " + model + "\n" + color + "\n$" + price + "\n" + mileage + " MILES\n");
    }

    public String getVIN()
    {
        return vin;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public int getPrice()
    {
        return price;
    }

    public int getMileage()
    {
        return mileage;
    }

    public String getColor()
    {
        return color;
    }

    public void setPrice(int newPrice)
    {
        price = newPrice;
    }

    public void setMileage(int newMileage)
    {
        mileage = newMileage;
    }

    public void setColor(String newColor)
    {
        color = newColor;
    }
}
