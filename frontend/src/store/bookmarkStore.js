import { create } from "zustand";
import bookmarkService from "../services/bookmark";

const useBookmarkStore = create((set) => ({
  bookmarkedPosts: [],
  loading: false,
  error: null,

  toggleBookmark: async (postId) => {
    set({ loading: true, error: null });
    try {
      const result = await bookmarkService.toggleBookmark(postId);

      return result;
    } catch (err) {
      set({
        error: err.response?.data.message || "Failed to toggle bookmark",
        loading: false,
      });
      throw err;
    }
  },

  getIsBookmarked: async (postId) => {
    set({ loading: true, error: null });
    try {
      const result = await bookmarkService.getIsBookmarked(postId);

      return result;
    } catch (err) {
      set({
        error: err.response?.data.message || "Failed to get bookmarked posts",
        loading: false,
      });
      throw err;
    }
  },

  getBookmarkedPosts: async () => {
    set({ loading: true, error: null });
    try {
      const content = await bookmarkService.getBookmarkedPosts();
      set({
        bookmarkedPosts: content,
        loading: false,
      });

      return content;
    } catch (err) {
      set({
        error: err.response?.data.message || "Failed to get bookmarked posts",
        loading: false,
      });
      throw err;
    }
  },
}));

export default useBookmarkStore;
