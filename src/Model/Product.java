/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 *
 * @author Tivinia Tonga
 * @version 14 February 2019
 */
public class Product {
    
    private ArrayList<Part> associatedParts;
    private int productID;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    
    public Product(int productID, String name, double price, int inStock, int min, int max)
    {
        associatedParts = new ArrayList();
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    public double getPrice()
    {
        return this.price;
    }
    
    public void setInStock(int inStock)
    {
        this.inStock = inStock;
    }
    
    public int getInStock()
    {
        return this.inStock;
    }
    
    public void setMin(int min)
    {
        this.min = min;
    }
    
    public int getMin()
    {
        return this.min;
    }
    
    public void setMax(int max)
    {
        this.max = max;
    }
    
    public int getMax()
    {
        return this.max;
    }
    
    public void addAssociatedPart(Part part)
    {
        this.associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partID)
    {
        for (Part part:this.associatedParts)
        {
            if (part.getPartID() == partID)
                return this.associatedParts.remove(part);
        }
        
        return false;
    }
    
    public Part lookupAssociatedPart(int partID)
    {
        for (Part part:this.associatedParts)
        {
            if (part.getPartID() == partID)
                return part;
        }
        
        throw new NoSuchElementException();
    }
    
    public void setProductID(int productID)
    {
        this.productID = productID;
    }
    
    public int getProductID()
    {
        return this.productID;
    }
    
    public ArrayList getAssocPartsList()
    {
        return this.associatedParts;
    }
}
