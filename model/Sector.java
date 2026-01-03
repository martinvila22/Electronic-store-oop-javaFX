package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sector implements Serializable {

    private static final long serialVersionUID = -444374744967224169L;

    private List<Item> items;
    private List<Supplier> suppliers;
    private List<Integer> quantities;
    private int nrOfItems;
    private String sectorName;

    private static final File outputFile = new File("src/dao/sectors.dat");

    // Constructor: Defensive copy of input lists
    public Sector(List<Item> items, String sectorName, int[] quantities, List<Supplier> suppliers) {
        this.items = (items != null) ? new ArrayList<>(items) : new ArrayList<>();
        this.suppliers = (suppliers != null) ? new ArrayList<>(suppliers) : new ArrayList<>();
        this.quantities = new ArrayList<>();
        if (quantities != null) {
            for (int q : quantities) {
                this.quantities.add(q);
            }
        }

        this.nrOfItems = 0;
        for (Item item : this.items) {
            this.nrOfItems += item.getQuantity();
        }

        this.sectorName = sectorName;

        saveSector();
    }



    public boolean isSectorEmpty() {
        return nrOfItems == 0;
    }

    public boolean isItemOutOfStock(Item item) {
        int index = items.indexOf(item);
        if (index >= 0) {
            return quantities.get(index) == 0;
        }
        return false;
    }

    public Item firstOutOfStockItem() {
        for (int i = 0; i < quantities.size(); i++) {
            if (quantities.get(i) == 0) {
                return items.get(i);
            }
        }
        return null;
    }

    public void addNewItem(Item newItem) {
        if (newItem != null) {
            items.add(newItem);
            quantities.add(newItem.getQuantity());
            nrOfItems += newItem.getQuantity();
            saveSector();
        }
    }

    public void deleteItem(Item item) {
        int index = items.indexOf(item);
        if (index >= 0) {
            nrOfItems -= quantities.get(index);
            items.remove(index);
            quantities.remove(index);
            saveSector();
        }
    }

    public void updateItemQuantity(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index >= 0) {
            nrOfItems = nrOfItems - quantities.get(index) + quantity;
            quantities.set(index, quantity);
            saveSector();
        }
    }


    public List<Item> getItems() {
        return Collections.unmodifiableList(items); // Prevent external modification
    }

    public List<Supplier> getSuppliers() {
        return Collections.unmodifiableList(suppliers);
    }

    public List<Integer> getQuantities() {
        return Collections.unmodifiableList(quantities);
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public String getSectorName() {
        return sectorName;
    }


    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
        saveSector();
    }


    private void saveSector() {
        List<Sector> allSectors = loadAllSectors();
        boolean found = false;

        // Replace sector if it exists
        for (int i = 0; i < allSectors.size(); i++) {
            if (allSectors.get(i).sectorName.equals(this.sectorName)) {
                allSectors.set(i, this);
                found = true;
                break;
            }
        }
        if (!found) {
            allSectors.add(this);
        }

        // Save all sectors at once (overwrite file)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputFile))) {
            oos.writeObject(allSectors);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Sector> loadAllSectors() {
        if (!outputFile.exists() || outputFile.length() == 0) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(outputFile))) {
            return (List<Sector>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Sector name: " + sectorName + "\n");
        sb.append("Number of items: ").append(nrOfItems).append("\n");
        sb.append("Items in sector:\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).getItemName())
                    .append(" - quantity: ").append(quantities.get(i))
                    .append("\n");
        }
        return sb.toString();
    }
}
