// 1. 迭代器接口
interface BidirectionalIterator {
    boolean hasNext();
    String next();
    boolean hasPrevious();
    String previous();
}

// 2. 聚合对象接口
interface Aggregate {
    BidirectionalIterator createIterator();
}

// 3. 具体聚合对象
class ProductCollection implements Aggregate {
    private String[] products;

    public ProductCollection(String[] products) {
        this.products = products;
    }

    @Override
    public BidirectionalIterator createIterator() {
        return new ProductBidirectionalIterator(this);
    }

    public String[] getProducts() {
        return products;
    }
}

// 4. 具体迭代器
class ProductBidirectionalIterator implements BidirectionalIterator {
    private ProductCollection collection;
    private int current;

    public ProductBidirectionalIterator(ProductCollection collection) {
        this.collection = collection;
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < collection.getProducts().length;
    }

    @Override
    public String next() {
        if (hasNext()) {
            return collection.getProducts()[current++];
        }
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return current > 0;
    }

    @Override
    public String previous() {
        if (hasPrevious()) {
            return collection.getProducts()[--current];
        }
        return null;
    }
}

// 5. 测试
public class SearchDemo {
    public static void main(String[] args) {
        String[] products = {"iPhone", "iPad", "MacBook", "Apple Watch"};
        Aggregate collection = new ProductCollection(products);
        BidirectionalIterator iterator = collection.createIterator();

        System.out.println("正向遍历：");
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("反向遍历：");
        while (iterator.hasPrevious()) {
            System.out.println(iterator.previous());
        }
    }
}
