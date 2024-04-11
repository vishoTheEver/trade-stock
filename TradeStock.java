import java.io.*;

public class TradeStock {

    static class ProfitResult {
        int buyIndex;
        int sellIndex;
        float profit;

        ProfitResult(int buyIndex, int sellIndex, float profit){
            this.buyIndex = buyIndex;
            this.sellIndex = sellIndex;
            this.profit = profit;
        }
    }

    public static ProfitResult findMaxProfit(float[] prices) {
        return findMaxProfitHelper(prices, 0, prices.length - 1);
    }
    private static ProfitResult findMaxProfitHelper(float[] prices, int start, int end) {
        if ( start >= end) return new ProfitResult(start, end, 0);
        int mid = start + (end - start) / 2;

        // Conquer
        ProfitResult leftResult = findMaxProfitHelper(prices, start, mid);
        ProfitResult rightResult = findMaxProfitHelper(prices, mid+1, end);

        // Combine
        float minLeftPrice = Float.MAX_VALUE;
        int minPriceIndex = start;

        for (int i = start; i <= mid; i++) {
            if (prices[i] < minLeftPrice) {
                minLeftPrice = prices[i];
                minPriceIndex = i;
            }
        }

        float maxRightPrice = Float.MIN_VALUE;
        int maxPriceIndex = mid + 1;
        for (int i = mid + 1; i <= end; i++){
            if (prices[i] > maxRightPrice){
                maxRightPrice = prices[i];
                maxPriceIndex = i;
            }
        }

        float crossProfit = maxRightPrice - minLeftPrice;

        // Decide the maximum of the three profits
        if (leftResult.profit > rightResult.profit && leftResult.profit > crossProfit) {
            return leftResult;
        } else if (rightResult.profit > crossProfit){
            return rightResult;
        } else {
            return new ProfitResult(minPriceIndex, maxPriceIndex, crossProfit);
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java TradeStock <file_name>");
            return;
        }
        String fileName = args[0];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            int numberOfPrices = dis.readInt();
            float[] prices = new float[numberOfPrices];

            for (int i = 0; i < numberOfPrices; i++) {
                prices[i] = dis.readFloat();
            }
            ProfitResult result = findMaxProfit(prices);
            System.out.println("Visho Malla Oli");
            System.out.println(fileName);
            System.out.println("Theta(n) Divide and Conquer");
            System.out.println(result.buyIndex + ", " + result.sellIndex + ", " + result.profit);

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading file: " + fileName);
        }

    }
}

