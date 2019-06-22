/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Tivinia Tonga
 * @version 13 February 2019
 */
public class InhousePart extends Part{
    
    private int machineID;
    
    public InhousePart (int partID, String name, double price, int inStock, int min, int max, int machineID)
    {
        this.setPartID(partID);
        this.setName(name);
        this.setPrice(price);
        this.setInStock(inStock);
        this.setMax(max);
        this.setMin(min);
        this.machineID = machineID;
    }
    
    public void setMachineID(int machineID)
    {
        this.machineID = machineID;
    }
    
    public int getMachineID()
    {
        return this.machineID;
    }
}
