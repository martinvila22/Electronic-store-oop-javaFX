package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Sector implements Serializable {


    public static class SectorLoadException extends RuntimeException {
        public SectorLoadException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public Sector(Sector other) {
        this.items = new ArrayList<>(other.getItems());
        this.suppliers = new ArrayList<>(other.getSuppliers());
        this.quantities = new ArrayList<>(other.getQuantities());
        this.nrOfItems = other.getNrOfItems();
        this.sectorName = other.getSectorName();
    }

    private static final long serialVersionUID = -444374744967224169L;

    private List<Item> items;
    private List<Supplier> suppliers;
    private List<Integer> quantities;
    private int nrOfItems;
    private String sectorName;

    private static final File OUTPUT_FILE = new File("src/dao/sectors.dat");

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
    }

    public void save() {
        List<Sector> allSectors = loadAllSectors();
        boolean replaced = false;

        for (int i = 0; i < allSectors.size(); i++) {
            if (allSectors.get(i).sectorName.equals(this.sectorName)) {
                allSectors.set(i, this);
                replaced = true;
                break;
            }
        }

        if (!replaced) {
            allSectors.add(this);
        }

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(allSectors);
        } catch (IOException e) {
            throw new SectorLoadException("Failed to save sectors", e);
        }
    }

    private static List<Sector> loadAllSectors() {
        if (!OUTPUT_FILE.exists() || OUTPUT_FILE.length() == 0) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            return (List<Sector>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new SectorLoadException("Failed to load sectors", e);
        }
    }

    public boolean isSectorEmpty() {
        return nrOfItems == 0;
    }

    public boolean isItemOutOfStock(Item item) {
        int index = items.indexOf(item);
        return index >= 0 && quantities.get(index) == 0;
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
        if (newItem == null) return;

        items.add(newItem);
        quantities.add(newItem.getQuantity());
        nrOfItems += newItem.getQuantity();
        save();
    }

    public void deleteItem(Item item) {
        int index = items.indexOf(item);
        if (index >= 0) {
            nrOfItems -= quantities.get(index);
            items.remove(index);
            quantities.remove(index);
            save();
        }
    }

    public void updateItemQuantity(Item item, int quantity) {
        int index = items.indexOf(item);
        if (index >= 0) {
            nrOfItems = nrOfItems - quantities.get(index) + quantity;
            quantities.set(index, quantity);
            save();
        }
    }


    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
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
        save();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Sector name: ").append(sectorName).append("\n");
        sb.append("Number of items: ").append(nrOfItems).append("\n");
        sb.append("Items in sector:\n");

        for (int i = 0; i < items.size(); i++) {
            sb.append(items.get(i).getItemName())
                    .append(" - quantity: ")
                    .append(quantities.get(i))
                    .append("\n");
        }
        return sb.toString();
    }

}
