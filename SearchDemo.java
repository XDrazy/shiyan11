// 1. 定义双向迭代器接口
interface BidirectionalIterator {
    boolean hasNext();
    String next();
    boolean hasPrevious();
    String previous();
}

// 2. 商品集合类
class ProductCollection {
    private String[] products;

    public ProductCollection(String[] products) {
        this.products = products;
    }

    // 工厂方法，创建双向迭代器
    public BidirectionalIterator createIterator() {
        return new ProductBidirectionalIterator(this);
    }

    public String[] getProducts() {
        return products;
    }
}

// 3. 双向迭代器实现
class ProductBidirectionalIterator implements BidirectionalIterator {
    private ProductCollection collection;
    private int current; // 指向当前位置

    public ProductBidirectionalIterator(ProductCollection collection) {
        this.collection = collection;
        this.current = 0; // 初始指向第一个元素
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

// 4. 测试
public class SearchDemo {
    public static void main(String[] args) {
        String[] products = {"iPhone", "iPad", "MacBook", "Apple Watch"};
        ProductCollection collection = new ProductCollection(products);
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
