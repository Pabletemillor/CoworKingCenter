package p7;

import java.util.Objects;

public class OfficeLocator implements Comparable<OfficeLocator>{
    private short floor;
    private short corridor;
    private short num;

    public OfficeLocator(short floor, short corridor, short num) {
        this.floor = floor;
        this.corridor = corridor;
        this.num = num;
    }
    public OfficeLocator(String locator) {
        String[] parts = locator.trim().split(":");
        if (parts.length != 3) {
            throw new IllegalArgumentException("localizador incorrecto: " + locator);
        }
    
        try {
            this.floor = Short.parseShort(parts[0]);
            this.corridor = Short.parseShort(parts[1]);
            this.num = Short.parseShort(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valores numericos incorrectos en: " + locator, e);
        }
    }
    
    public short getFloor() {
        return floor;
    }

    public void setFloor(short floor) {
        this.floor = floor;
    }

    public short getCorridor() {
        return corridor;
    }

    public void setCorridor(short corridor) {
        this.corridor = corridor;
    }

    public short getNum() {
        return num;
    }

    public void setNum(short num) {
        this.num = num;
    }

    @Override
    public int compareTo(OfficeLocator o) {
    int cmp = Short.compare(o.floor, this.floor);
    if (cmp != 0) return cmp;
    cmp = Short.compare(this.corridor, o.corridor);
    if (cmp != 0) return cmp;
    return Short.compare(this.num, o.num);
}
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof OfficeLocator)) return false;
        OfficeLocator that = (OfficeLocator) object;
        return floor == that.floor && corridor == that.corridor && num == that.num;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor, corridor, num);
    }

    @Override
    public String toString() {
        return floor + ":" + corridor + ":" + num;
    }
}
