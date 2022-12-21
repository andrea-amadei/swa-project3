import './ReplyCard.scss'

function ReplyCard({ reply }) {
  
  const datetime = new Date(reply.created_at);
  const date = datetime.toLocaleDateString(undefined,{dateStyle: 'short'});
  const time = datetime.toLocaleTimeString();
  
  return (
    <div className="reply">
      <div className="content"><pre>{reply.content}</pre></div>
      <div className="details">
        <div className="time">{date} {time}{(localStorage.getItem("session_token") === reply.created_by ? ' - You' : '')}</div>
      </div>
    </div>
  );
}

export default ReplyCard;