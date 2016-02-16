import java.util.Scanner;

public class CarTracker
{
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        String input;
        int carCounter = 1;
        IndexMinPQ<Car> pq = new IndexMinPQ<Car>(10000);
        System.out.println("\n\nWELCOME TO THE CS 1501 CAR TRACKER!\n");
        while (true)
        {
            System.out.println("\nWould you like to add, update, remove, or retreive the information of a car?");
            System.out.println("Type 'a' for add, 'u' for update, 'r' for remove, 'rt' for retreive, or 'q' to quit.\n");
            input = keyboard.nextLine();
            if (input.equals("a"))
            {
                while(true)
                {
                    System.out.println("\nType all the information of the car, separated by spaces, in this format:");
                    System.out.println("[VIN] [Make] [Model] [Price] [Mileage] [Color]\nJN1AZ4EHXEM632250 Honda Accord 16595.00 28000 White\n");
                    input = keyboard.nextLine();
                    String[] newCar = input.split(" ");
                    if (newCar.length == 6)
                    {
                        Car temp = new Car(newCar[0].toUpperCase(), newCar[1].toUpperCase(), newCar[2].toUpperCase(), Integer.parseInt(newCar[3]), Integer.parseInt(newCar[4]), newCar[5].toUpperCase());
                        pq.insertPrice(carCounter, temp);
                        pq.insertMileage(carCounter, temp.cloneToMileage());
                        System.out.println("\nThe following car was added.\n");
                        temp.printCar();
                        carCounter++;
                        break;
                    }
                    else
                        System.out.println("Please enter a car in the correct format.\n\n");
                }
            }
            else if (input.equals("u"))
            {
                if (pq.size() == 0)
                {
                    System.out.println("\nERROR: There are no cars stored in the car tracker.");
                }
                else
                {
                    System.out.println("\nPlease enter the VIN number of the car you would like to update:\n");
                    input = keyboard.nextLine();
                    boolean updated = false;
                    for (int i = 1; i < pq.size() + 1; i++)
                    {
                        if (updated)
                        {
                            break;
                        }
                        if (pq.contains(i))
                        {
                            if (((pq.traversePQPrice(i)).getVIN()).equals(input))
                            {
                                while (true)
                                {
                                    System.out.println("\nWhich attribute would you like to update?");
                                    System.out.println("Type 'price' for price, 'mileage' for mileage, or 'color' for color.\n");
                                    input = keyboard.nextLine();
                                    if (input.equals("price"))
                                    {
                                        System.out.println("\nEnter the new price:");
                                        input = keyboard.nextLine();
                                        Car temp = new Car(pq.traversePQPrice(i).getVIN(), pq.traversePQPrice(i).getMake(), pq.traversePQPrice(i).getModel(), Integer.parseInt(input), pq.traversePQPrice(i).getMileage(), pq.traversePQPrice(i).getColor());
                                        pq.changeKeyPrice(pq.indexPrice(i), temp);
                                        pq.changeKeyMileage(pq.indexPrice(i), temp.cloneToMileage());
                                        updated = true;
                                        System.out.println("\nUpdated car:\n");
                                        temp.printCar();
                                        break;
                                    }
                                    else if (input.equals("mileage"))
                                    {
                                        System.out.println("\nEnter the new mileage:");
                                        input = keyboard.nextLine();
                                        Car temp = new Car(pq.traversePQPrice(i).getVIN(), pq.traversePQPrice(i).getMake(), pq.traversePQPrice(i).getModel(), pq.traversePQPrice(i).getPrice(), Integer.parseInt(input), pq.traversePQPrice(i).getColor());
                                        pq.changeKeyPrice(pq.indexPrice(i), temp);
                                        pq.changeKeyMileage(pq.indexPrice(i), temp.cloneToMileage());
                                        updated = true;
                                        System.out.println("\nUpdated car:\n");
                                        temp.printCar();
                                        break;
                                    }
                                    else if (input.equals("color"))
                                    {
                                        System.out.println("\nEnter the new color:");
                                        input = keyboard.nextLine();
                                        Car temp = new Car(pq.traversePQPrice(i).getVIN(), pq.traversePQPrice(i).getMake(), pq.traversePQPrice(i).getModel(), pq.traversePQPrice(i).getPrice(), pq.traversePQPrice(i).getMileage(), input.toUpperCase());
                                        pq.changeKeyPrice(pq.indexPrice(i), temp);
                                        pq.changeKeyMileage(pq.indexPrice(i), temp.cloneToMileage());
                                        updated = true;
                                        System.out.println("\nUpdated car:\n");
                                        temp.printCar();
                                        break;
                                    }
                                    else
                                    {
                                        System.out.println("Please enter a valid attribute.\n\n");
                                    }
                                }
                            }
                        }
                    }
                }
                if (!updated)
                {
                    System.out.println("Car with VIN" + input.toUpperCase() + " not found.");
                }
            }
            else if (input.equals("r"))
            {
                if (pq.size() == 0)
                {
                    System.out.println("\nERROR: There are no cars stored in the car tracker.");
                }
                else
                {
                    while (true)
                    {
                        boolean found = false;
                        System.out.println("\nWould you like to remove a car by its VIN or would you like to remove the minimum mileage or price car?");
                        System.out.println("Type 'vin' for removal by VIN, 'minm' for removal of the lowest mileage car, or 'minp' for removal of the lowest price car.\n");
                        input = keyboard.nextLine();
                        if (input.equals("vin"))
                        {
                            System.out.println("\nEnter the VIN number:\n");
                            input = keyboard.nextLine().toUpperCase();
                            for (int i = 0; i < pq.size() + 1; i++)
                            {
                                if (found)
                                {
                                    break;
                                }
                                if (pq.contains(i))
                                {
                                    if (((pq.traversePQPrice(i)).getVIN()).equals(input))
                                    {
                                        pq.deletePrice(i);
                                        pq.deleteMileage(i);
                                        found = true;
                                        System.out.println("\nThe car with the VIN " + input.toUpperCase() + " has been removed.\n");
                                    }
                                }
                            }
                            if (!found)
                            {
                                System.out.println("\nA car with the VIN " + input + " could not be found.\n");
                            }
                        }
                        else if (input.equals("minm"))
                        {
                            String vin = pq.traversePQMileage(1).getVIN();
                            pq.finishDelMinMileage(pq.delMinMileage());
                            System.out.println("\nThe car with the VIN " + vin + " has been removed.\n");
                            found = true;
                        }
                        else if (input.equals("minp"))
                        {
                            String vin = pq.traversePQPrice(1).getVIN();
                            pq.finishDelMinPrice(pq.delMinPrice());
                            System.out.println("\nThe car with the VIN " + vin + " has been removed.\n");
                            found = true;
                        }
                        else
                        {
                            System.out.println("\nPlease enter a valid method of removal.\n");
                        }

                        if (found)
                        {
                            break;
                        }
                    }
                }
            }
            else if (input.equals("rt"))
            {
                if (pq.size() == 0)
                {
                    System.out.println("\nERROR: There are no cars stored in the car tracker.");
                }
                else
                {
                    while (true)
                    {
                        System.out.println("\nWould you like to retreive from a particular make/model or from all cars?");
                        System.out.println("Type 'm' for a particular make/model or 'a' for all cars.\n");
                        input = keyboard.nextLine();
                        if (input.equals("m"))
                        {
                            while (true)
                            {
                                System.out.println("\nEnter the make and model, separated by a space. For example:");
                                System.out.println("Audi A4\n");
                                input = keyboard.nextLine();
                                String[] car = input.split(" ");
                                if (car.length == 2)
                                {
                                    while (true)
                                    {
                                        System.out.println("\nWould you like to retreive the lowest price or lowest mileage " + input.toUpperCase() + "?");
                                        System.out.println("Type 'p' for lowest price and 'm' for lowest mileage.\n");
                                        input = keyboard.nextLine();
                                        if (input.equals("p"))
                                        {
                                            boolean found = false;
                                            int max = 999999999;
                                            int loc;
                                            for (int i = 0; i < pq.size() + 1; i++)
                                            {
                                                if (pq.contains(i))
                                                {
                                                    if (((pq.traversePQPrice(i)).getMake()).equals(car[0].toUpperCase()) && ((pq.traversePQPrice(i)).getModel()).equals(car[1].toUpperCase()))
                                                    {
                                                        if (max > (pq.traversePQMileage(i)).getMileage())
                                                        {
                                                            max = (pq.traversePQMileage(i)).getMileage();
                                                            loc = i;
                                                        }
                                                        found = true;
                                                    }
                                                }
                                                if (i == pq.size())
                                                {
                                                    (pq.traversePQPrice(i)).printCar();
                                                }
                                                else if (i == pq.size() && !found)
                                                {
                                                    System.out.println("Make/Model not found.");
                                                }
                                            }
                                            break;
                                        }
                                        else if (input.equals("m"))
                                        {
                                            int max = 999999999;
                                            int loc;
                                            boolean found = false;
                                            for (int i = 0; i < pq.size() + 1; i++)
                                            {
                                                if (pq.contains(i))
                                                {
                                                    if (((pq.traversePQMileage(i)).getMake()).equals(car[0].toUpperCase()) && ((pq.traversePQMileage(i)).getModel()).equals(car[1].toUpperCase()))
                                                    {
                                                        if (max > (pq.traversePQMileage(i)).getMileage())
                                                        {
                                                            max = (pq.traversePQMileage(i)).getMileage();
                                                            loc = i;
                                                        }
                                                        found = true;
                                                    }
                                                }
                                                if (i == pq.size())
                                                {
                                                    (pq.traversePQMileage(i)).printCar();
                                                }
                                                else if (i == pq.size() && !found)
                                                {
                                                    System.out.println("Make/Model not found.");
                                                }
                                            }
                                            break;
                                        }
                                        else
                                        {
                                            System.out.println("Please enter a valid command.");
                                        }
                                    }
                                    break;
                                }
                            }
                            break;
                        }
                        else if (input.equals("a"))
                        {
                            while (true)
                            {
                                System.out.println("\nWould you like to retreive the lowest mileage car or lowest price car?");
                                System.out.println("Type 'm' for mileage or 'p' for price.\n");
                                input = keyboard.nextLine();
                                if (input.equals("m"))
                                {
                                    System.out.println("\n");
                                    pq.traversePQMileage(1).printCar();
                                    break;
                                }
                                else if (input.equals("p"))
                                {
                                    System.out.println("\n");
                                    pq.traversePQPrice(1).printCar();
                                    break;
                                }
                                else
                                {
                                    System.out.println("\nPlease enter a valid command.\n");
                                }
                            }
                            break;
                        }
                        else
                        {
                            System.out.println("\nPlease enter a valid command.\n");
                        }
                    }
                }
            }
            else if (input.equals("q"))
            {
                System.exit(0);
            }
            else
            {
                System.out.println("Please enter a valid command.\n\n");
            }
        }
    }
}
