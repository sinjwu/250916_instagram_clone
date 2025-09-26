import PostCard from "./PostCard";

const PostList = ({ posts }) => {
  return (
    <div className="space-y-4">
      {(Array.isArray(posts) ? posts : []).map((post) => (
        <PostCard key={post.id} post={post} />
      ))}
    </div>
  );
};

export default PostList;
