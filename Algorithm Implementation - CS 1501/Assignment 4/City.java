public class City
{
    private String name;
    private int distance;
    private double cost;

    public City(String nameArg)
    {
        name = nameArg;
    }

    public City(String nameArg, int distanceArg, double costArg)
    {
        name = nameArg;
        distance = distanceArg;
        cost = costArg;
    }

    public String getName()
    {
        return name;
    }

    public int getDistance()
    {
        return distance;
    }

    public double getCost()
    {
        return cost;
    }

    public void setName(String nameArg)
    {
        name = nameArg;
    }

    public void setDistance(int distanceArg)
    {
        distance = distanceArg;
    }

    public void setCost(double costArg)
    {
        cost = costArg;
    }
}
