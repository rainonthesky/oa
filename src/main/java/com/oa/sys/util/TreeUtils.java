package com.oa.sys.util;

import java.util.List;
import java.util.Map;

public class TreeUtils {




    /**
     * 对于菜单列表进行一个父子结构的排序
     */
    public static <T> void sortTreeList(List<T> sortTreeList, List<T> treeList, Long parentId) {
        for (int i = 0; i < treeList.size(); i++) {
            TreeDto m = (TreeDto) treeList.get(i);
            //找第一级
            if (m.getParentId() != null && m.getParentId().equals(parentId)) {
                sortTreeList.add((T) m);
                       //递归
                sortTreeList(sortTreeList, treeList, m.getId());

                    }
                }
            }

//    public static <T> void sortTreeList(List<T> sortTreeList, List<T> treeList, Long parentId) {
//        for (int i = 0; i < treeList.size(); i++) {
//            TreeDto m = (TreeDto) treeList.get(i);
//            //找第一级
//            if (m.getParentId() != null && m.getParentId().equals(parentId)) {
//                sortTreeList.add((T) m);
//                for (T child : treeList) {
//                    if (((TreeDto) child).getParentId() != null && ((TreeDto) child).getParentId().equals(parentId)) {
//                        //递归
//                        sortTreeList(sortTreeList, treeList, m.getId());
//                        break;
//                    }
//                }
//            }
//        }
//    }
    public static Map<Long, TreeDto> getAllChildrenMap(Map<Long, TreeDto> childrenMap,
                                                       List<TreeDto> treeList, long parentId) {

        for (TreeDto tree : treeList) {
            if (tree.getParentId().longValue() == parentId) {
                childrenMap.put(tree.getId(), tree);
                getAllChildrenMap(childrenMap, treeList, tree.getId());
            }
        }
        return childrenMap;

    }
}


