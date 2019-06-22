/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Tivinia Tonga
 */
public class OutsourcedPart extends Part{
    
    private String companyName;
    
    public OutsourcedPart (int partID, String name, double price, int inStock, int min, int max, String companyName)
    {
        this.setPartID(partID);
        this.setName(name);
        this.setPrice(price);
        this.setInStock(inStock);
        this.setMax(max);
        this.setMin(min);
        this.companyName = companyName;
    }
    
    public String getCompanyName()
    {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
}
