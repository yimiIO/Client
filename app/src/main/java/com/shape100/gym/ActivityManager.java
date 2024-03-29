package com.shape100.gym;

import java.util.Stack;

import android.app.Activity;

public class ActivityManager {
	private static Stack<Activity> stack = new Stack<Activity>();

	public static void popAll() {
		while (!stack.isEmpty()) {
			pop();
		}
	}

	public static void pop() {
		Activity activity = stack.pop();
		if (activity != null && !activity.isFinishing()) {
			activity.finish();
		}
	}

	public static void pop(Activity activity) {
		if (!activity.isFinishing()) {
			activity.finish();
		}
		stack.remove(activity);
	}

	public static void popClass(Class<? extends Activity> cls) {
		Stack<Activity> newStack = new Stack<Activity>();
		for (Activity a : stack) {
			if (a.getClass().equals(cls)) {
				if (!a.isFinishing()) {
					a.finish();
				}
			} else {
				newStack.push(a);
			}
		}
		stack = newStack;
	}

	public static Activity current() {
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	public static void push(Activity activity) {
		popClass(activity.getClass());
		stack.push(activity);
	}

	public static void retain(Class<? extends Activity> cls) {
		Stack<Activity> newStack = new Stack<Activity>();
		for (Activity a : stack) {
			if (a.getClass().equals(cls)) {
				newStack.push(a);
			} else {
				if (!a.isFinishing()) {
					a.finish();
				}
			}
		}
		stack = newStack;
	}
}
