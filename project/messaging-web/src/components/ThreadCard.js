import './ThreadCard.scss'
import {Link} from "react-router-dom";

function ThreadCard({ thread, color, onClick }) {
  
  const datetime = new Date(thread.created_at);
  const date = datetime.toLocaleDateString(undefined,{dateStyle: 'short'});
  const time = datetime.toLocaleTimeString();
  
  return (
    <Link to={"/" + thread.thread_ID} onClick={onClick} >
      <div className="thread" style={ {backgroundColor: color} }>
        <div className="content"><pre>{thread.content}</pre></div>
        <div className="details">
          <div className="replies">{thread.replies + (thread.replies === 1 ? " reply" : " replies")}</div>
          <div className="time">{date} {time}{(localStorage.getItem("session_token") === thread.created_by ? ' - You' : '')}</div>
        </div>
      </div>
    </Link>
  );
}

export default ThreadCard;