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
 * @version 13 February 2019
 */
public class Inventory {
    
    private ArrayList<Product> products;
    private ArrayList<Part> allParts;
    
    public Inventory()
    {
        this.products = new ArrayList<>();
        this.allParts = new ArrayList<>();
    }
    
    public void addProduct(Product product)
    {
        this.products.add(product);
    }
    
    public boolean removeProduct(int productID)
    {
        for (Product product:this.products)
        {
            if (product.getProductID() == productID)
            {
                return this.products.remove(product);
            }
        }
        return false;
    }
    
    public Product lookupProduct(int productID)
    {
        for (Product product:this.products)
        {
            if (product.getProductID() == productID)
            {
                return product;
            }
        }
        
        throw new NoSuchElementException();        
    }
    
    public void updateProduct(int productID)
    {
        //TODO
    }
    
    public void updateProduct(Product product)
    {
        for (int i = 0; i < this.products.size(); i++)
        {
            if (this.products.get(i).getProductID() == product.getProductID())
            {
                this.products.set(i, product);
                return;
            }
        }
        
        throw new NoSuchElementException();
    }
    
    public void addPart(Part part)
    {
        allParts.add(part);
    }
    
    public boolean deletePart(int partID)
    {
        for (Part part:allParts)
        {
            if (part.getPartID() == partID)
            {
                return allParts.remove(part);
            }
        }
        
        return false;
    }
    
    public Part lookupPart(int partID)
    {
        for (Part part:allParts)
        {
            if (part.getPartID() == partID)
            {
                return part;
            }
        }
        
        throw new NoSuchElementException();
    }
    
    public void updatePart(int partID)
    {
        //TODO
    }
    
    public void updatePart(Part part)
    {
        for (int i = 0; i < this.allParts.size(); i++)
        {
            if (this.allParts.get(i).getPartID() == part.getPartID())
            {
                this.allParts.set(i, part);
                return;
            }
        }        
        throw new NoSuchElementException();
    }
    
    public ArrayList getProdList()
    {
        return products;
    }
    
    public ArrayList getPartsList()
    {
        return allParts;
    }
    
}
