package com.yumaolin.deepunderstand.leetcode.algorithm;

import java.util.Arrays;
import java.util.List;

/**
 * 2. 两数相加
 * 
 * @author yuml
 * @since 2019年9月18日
 */
public class AddTwoNumbers {
	
	private static final char ZERO = '0';
	
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		String a = getListNodeValue(l1);
		String b = getListNodeValue(l2);
		String c = strAdd(a,b);
		return intToNode(c);
	}
	
	/**
	 * <p>此方法是精解</p>
	 * @param l1
	 * @param l2
	 * @author guanpengchn
	 * @link https://leetcode-cn.com/problems/add-two-numbers/solution/hua-jie-suan-fa-2-liang-shu-xiang-jia-by-guanpengc/
	 */
	public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;
        while(l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;
            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);
            cur = cur.next;
            if(l1 != null)
                l1 = l1.next;
            if(l2 != null)
                l2 = l2.next;
        }
        if(carry == 1) {
            cur.next = new ListNode(carry);
        }
        return pre.next;
    }

	/**
	 * <p>获取链表中的值</p>
	 * @param listNode
	 * @return
	 */
	public String getListNodeValue(ListNode listNode) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(listNode.val);
		ListNode nextListNode = listNode.next;
		while(nextListNode != null) {
			sBuilder.append(nextListNode.val);
			nextListNode = nextListNode.next;
		}
		char[] maxArrays = sBuilder.toString().toCharArray();
		for (int start = 0, end = maxArrays.length - 1; start < end; start++, end--) {
			char temp = maxArrays[end];
			maxArrays[end] = maxArrays[start];
			maxArrays[start] = temp;
	    }
		return String.valueOf(maxArrays);
	}
	
	/**
	 * <p>字符串加法</p>
	 * @param listNode
	 * @return
	 */
	public String strAdd(String a,String b) {
		int aLength = a.length();
		int bLength = b.length();
		String maxString = null;
		String minString = null;
		int maxLength = 0;
		int minLength = 0;
		if(aLength > bLength) {
			maxLength = aLength;
			maxString = a;
			minLength = bLength;
			minString = b;
		}else {
			maxLength = bLength;
			maxString = b;
			minLength = aLength;
			minString = a;
		}
		char[] maxArrays = maxString.toCharArray();
		for(int i=1,k=maxLength;i<=k;i++) {
			int maxCur = maxLength-i;
			int minCur = minLength-i;
			if(minCur<0) {
				break;
			}
			int aInt = (maxArrays[maxCur] - ZERO) + (minString.charAt(minCur)- ZERO);
			do {
				//如果进位 就相继续处理上一个
				if(aInt >= 10) {
					char cChar = (char) (aInt - 10 + ZERO);
					maxArrays[maxCur] = cChar;
					if(i == maxLength || maxCur == 0) {
					    ++maxLength;
						char[] newMaxArrays = new char[maxLength];
						System.arraycopy(maxArrays, 0, newMaxArrays, 1, maxLength-1);
						newMaxArrays[maxCur] = 49;
						maxArrays = newMaxArrays;
						aInt = 0;
					} else {
						--maxCur;
						aInt = (maxArrays[maxCur] - ZERO) + 1;
						if(aInt < 10) {
							maxArrays[maxCur] = (char) (aInt + ZERO);
						}
					}
				} else {
					maxArrays[maxCur] = (char) (aInt + ZERO);
				}
			}while(aInt >= 10);
		}
		return String.valueOf(maxArrays);
	}

	/**
	 * <p>字符串生成链表</p>
	 * @param value
	 * @return
	 */
	public ListNode intToNode(String value) {
		int length = value.length();
		ListNode rootListNode = new ListNode(value.charAt(length-1)-ZERO);
		for(int i=length-2;i>=0;i--) {
			ListNode listNode = new ListNode(value.charAt(i)-ZERO);
			ListNode currentListNode = rootListNode;
			ListNode nextListNode = rootListNode.next;
			while (nextListNode != null) {
				currentListNode = nextListNode;
				nextListNode = nextListNode.next;
			}
			currentListNode.next = listNode;
		}
		return rootListNode;
	}

	class ListNode {
		int val;
		ListNode next;
		ListNode(int x) {
			val = x;
		}
	}
	
	public static void main(String[] args) {
		AddTwoNumbers addTwoNumbers = new AddTwoNumbers();
		ListNode l1 = addTwoNumbers.testCreateListNode(Arrays.asList(1,8));
		ListNode l2 = addTwoNumbers.testCreateListNode(Arrays.asList(0));
		long start = System.currentTimeMillis();
		ListNode listNode = addTwoNumbers.addTwoNumbers(l1, l2);
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(addTwoNumbers.testGetListNodeValue(listNode));
		ListNode listNode2 = addTwoNumbers.addTwoNumbers2(l1, l2);
		System.out.println(addTwoNumbers.testGetListNodeValue(listNode2));
	}
	
	/**
	 * <p>生成测试用的listNode</p>
	 * @param list
	 * @return
	 */
	private ListNode testCreateListNode(List<Integer> list) {
		ListNode rootListNode = null;
		if(list !=null && !list.isEmpty()) {
			rootListNode = new ListNode(list.get(0));
			for(int i=1,k=list.size();i<k;i++) {
				ListNode listNode = new ListNode(list.get(i));
				ListNode currentListNode = rootListNode;
				ListNode nextListNode = rootListNode.next;
				while (nextListNode != null) {
					currentListNode = nextListNode;
					nextListNode = nextListNode.next;
				}
				currentListNode.next = listNode;
			}
		}
		return rootListNode;
	}
	
	public String testGetListNodeValue(ListNode listNode) {
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append(listNode.val);
		ListNode nextListNode = listNode.next;
		while(nextListNode != null) {
			sBuilder.append(nextListNode.val);
			nextListNode = nextListNode.next;
		}
		return sBuilder.toString();
	}

}
