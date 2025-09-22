import { useEffect } from "react";
import usePostStore from "../../store/postStore";
import PostCard from "./PostCard";

const PostList = () => {
  const { posts, fetchPosts } = usePostStore();

  const loadPosts = async () => {
    fetchPosts();
  };

  useEffect(() => {
    loadPosts();
  }, []);

  return (
    <div className="space-y-4">
      {(Array.isArray(posts) ? posts : []).map((post) => (
        <PostCard key={post.id} post={post} />
      ))}
    </div>
  );
};

export default PostList;
