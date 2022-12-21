import './NewCard.scss'
import {useState} from "react";

function NewCard({ width, title, onSubmit }) {
  
  const [content, setContent] = useState('');
  const [nLines, setNLines] = useState(1);
  
  const handleChange = (e) => {
    setContent(e.target.value);
    
    setNLines(e.target.value.split('\n').length);
  }
  
  const handleSubmit = () => {
    onSubmit(content);
    setContent('');
  }
  
  return (
    <div className="new-card" style={{width: width}}>
      <textarea onChange={handleChange} value={content} rows={nLines} />
      <div className="new-thread-header">
        <h3>{title}</h3>
        <button onClick={handleSubmit} >Post</button>
      </div>
    </div>
  );
}

export default NewCard;