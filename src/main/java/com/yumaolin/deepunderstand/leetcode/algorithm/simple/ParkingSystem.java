package com.yumaolin.deepunderstand.leetcode.algorithm.simple;

/**
 * @author yml
 * @Description 停车位问题
 * @Date 2021-03-19 09:57
 */
public class ParkingSystem {

    private final int[] parkings = new int[3];

    public ParkingSystem(int big, int medium, int small) {
        this.parkings[0] = big;
        this.parkings[1] = medium;
        this.parkings[2] = small;
    }

    public boolean addCar(int carType) {
        boolean isFlag = false;
        switch (carType){
            case 1:
                isFlag = this.getFlag(0);
                break;
            case 2:
                isFlag = this.getFlag(1);
                break;
            case 3:
                isFlag = this.getFlag(2);
                break;
            default:
                break;
        }
        return isFlag;
    }

    private boolean getFlag(int index){
        boolean isFlag = false;
        int big = parkings[index];
        if(big > 0){
            isFlag=true;
            parkings[index]=big-1;
        }
        return isFlag;
    }

    public static void main(String[] args) {
        ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
        System.out.println(parkingSystem.addCar(1)); // 返回 true ，因为有 1 个空的大车位
        System.out.println(parkingSystem.addCar(2)); // 返回 true ，因为有 1 个空的中车位
        System.out.println(parkingSystem.addCar(3)); // 返回 false ，因为没有空的小车位
        System.out.println(parkingSystem.addCar(1)); // 返回 false ，因为没有空的大车位，唯一一个大车位已经被占据了
    }

}
