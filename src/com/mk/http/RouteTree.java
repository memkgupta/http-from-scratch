package com.mk.http;

public class RouteTree {

    private static volatile RouteTree instance; // volatile for thread safety
    public final RouteNode root;

    // private constructor so no one can do new RouteTree()
    private RouteTree() {
        this.root = new RouteNode("");
    }

    // static factory method to get the single instance
    public static RouteTree getInstance() {
        if (instance == null) {
            synchronized (RouteTree.class) {
                if (instance == null) {
                    instance = new RouteTree();
                }
            }
        }
        return instance;
    }

    public void registerRouteRequestHandler(RouteNode root, String[] paths, int index, RequestHandler requestHandler) {
        if (index >= paths.length) return;

        String key = paths[index];
        RouteNode routeNode = root.childrens.computeIfAbsent(key, RouteNode::new);

        if (index == paths.length - 1) {
            routeNode.requestHandler = requestHandler;
            return;
        }

        registerRouteRequestHandler(routeNode, paths, index + 1, requestHandler);
    }

    public RequestHandler getMatchingRoute(String[] path) {
        RouteNode traverse = root;

        for (String key : path) {
            RouteNode routeNode = traverse.childrens.get(key);

            if (routeNode == null) {
                // check for dynamic key (like :id)
                String dynamicKey = traverse.childrens.keySet().stream()
                        .filter(k -> k.startsWith(":"))
                        .findFirst()
                        .orElse(null);

                if (dynamicKey == null) return null;

                routeNode = traverse.childrens.get(dynamicKey);
            }

            traverse = routeNode;
        }

        return traverse.requestHandler; // null if no match
    }
}
