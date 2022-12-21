import './ElementPage.scss'
import ThreadCard from "../components/ThreadCard";
import {useEffect, useState} from "react";
import ReplyCard from "../components/ReplyCard";
import NewCard from "../components/NewCard";
import {addReplyToThread, getAllRepliesOnThread} from "../utils/fetch";

function ElementPage({ selectedElement }) {
  const [replies, setReplies] = useState([]);
  
  const handleAddReply = (content) => {
    addReplyToThread(content, selectedElement.thread.thread_ID);
    setTimeout(() => getAllRepliesOnThread(selectedElement.thread.thread_ID, setReplies), 200);
  }
  
  useEffect(() => {
    if(selectedElement === undefined)
      window.location = "/";
    
  }, [selectedElement]);
  
  useEffect(() => {
    getAllRepliesOnThread(selectedElement.thread.thread_ID, setReplies)
    const interval = setInterval(() => getAllRepliesOnThread(selectedElement.thread.thread_ID, setReplies, true), 20_000);
    
    return () => clearInterval(interval);
  }, []);
  
  return (
    <div className="element">
      <ThreadCard thread={{...selectedElement?.thread, replies: replies.length}} color={selectedElement?.color}
        onClick={() => {}}
      />
  
      {
        replies.map(x => <ReplyCard reply={x} key={x.reply_ID} />)
      }
  
      <NewCard width={600} title="Add Reply" onSubmit={handleAddReply} />
    </div>
  )
}

export default ElementPage;
