import './HomePage.scss'
import ThreadCard from "../components/ThreadCard";
import {hslToHex} from "../utils/colors";
import {useEffect, useState} from "react";
import NewCard from "../components/NewCard";
import {addThread, getAllThreads} from "../utils/fetch";

const nColors = 10;

const colors = Array
  .from({length: nColors},(v, k) => k * (360 / nColors))
  .map(x => hslToHex(x, 50, 50));

function HomePage({ setSelectedThread }) {
  
  const[threads, setThreads] = useState([]);
  
  const handleAddPost = (content) => {
    addThread(content);
    setTimeout(() => getAllThreads(setThreads), 200);
  }
  
  useEffect(() => setSelectedThread(undefined), []);
  
  useEffect(() => {
    getAllThreads(setThreads);
    const interval = setInterval(() => getAllThreads(setThreads, true), 20_000);
    
    return () => clearInterval(interval);
  }, []);
  
  return (
    <div className="home">
      <NewCard width={800} title="Create Thread" onSubmit={handleAddPost} />
      
      {
        threads.map((x, i) => <ThreadCard thread={x} color={colors[i % nColors]} key={x.thread_ID}
                                        onClick={() => setSelectedThread({thread: x, color: colors[i % nColors]})}
                                      />
        )
      }
    </div>
  );
}

export default HomePage;