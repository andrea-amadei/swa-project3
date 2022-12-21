export const BASE_ADDRESS = ''

function getToken() {
  let sessionToken = localStorage.getItem('session_token');
  
  if(sessionToken == null)
    sessionToken = 'x';
  
  return sessionToken;
}

function checkIfTokenIsDifferent(sentToken) {
  let localToken = localStorage.getItem('session_token');
  
  if(localToken !== sentToken) {
    localStorage.removeItem('session_token');
    window.location.reload();
  }
}

export const getAllThreads = (setter, debug = false) => {
  
  fetch(BASE_ADDRESS + '/api/threads', {
    method: 'GET',
    headers: {'Authorization': getToken()}
  })
  .then(data => data.json())
  .catch(error => console.log(error))
  
    .then(response => {
      checkIfTokenIsDifferent(response.session.session_token);
      localStorage.setItem('session_token', response.session.session_token);
      
      setter(response.response);
      
      if(debug)
        console.log(response.response);
    });
}

export const getAllRepliesOnThread = (threadID, setter, debug = false) => {
  
  fetch(BASE_ADDRESS + '/api/replies?threadID=' + threadID, {
    method: 'GET',
    headers: {'Authorization': getToken()}
  })
    .then(data => data.json())
    .catch(error => console.log(error))
    
    .then(response => {
      checkIfTokenIsDifferent(response.session.session_token);
      localStorage.setItem('session_token', response.session.session_token);
      
      setter(response.response);
      
      if(debug)
        console.log(response.response);
    });
}

export const addThread = (content) => {
  fetch(BASE_ADDRESS + '/api/threads', {
    method: 'POST',
    headers: {'Authorization': getToken()},
    body: content
  })
    .then(data => data.json())
    .catch(error => console.log(error))
    
    .then(response => {
      checkIfTokenIsDifferent(response.session.session_token);
      localStorage.setItem('session_token', response.session.session_token);
    });
}

export const addReplyToThread = (content, threadID) => {
  fetch(BASE_ADDRESS + '/api/replies?threadID=' + threadID, {
    method: 'POST',
    headers: {'Authorization': getToken()},
    body: content
  })
    .then(data => data.json())
    .catch(error => console.log(error))
    
    .then(response => {
      checkIfTokenIsDifferent(response.session.session_token);
      localStorage.setItem('session_token', response.session.session_token);
    });
}
