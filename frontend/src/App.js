import React, { useState } from 'react';

function App() {
  const [url, setUrl] = useState('');
  const [result, setResult] = useState([]);
  const [loading, setLoading] = useState(false);

 const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);
  setResult([]);

  try {
    const res = await fetch(
      `http://localhost:8080/api/analyse?url=${encodeURIComponent(url)}`,
      {
        method: "POST",
        headers: {
          "Accept": "application/json"
        }
      }
    );

    if (!res.ok) {
      throw new Error(`Server error: ${res.status}`);
    }

    const data = await res.json();
    console.log("Data from backend:", data);
    setResult(data);
  } catch (err) {
    console.error("Error fetching data", err);
    alert("Something went wrong!");
  }

  setLoading(false);
};

  return (
    <div style={{ padding: "20px" }}>
      <h2>Word Statistics Analyzer</h2>
      <form onSubmit={handleSubmit}>
        <input
          style={{ width: "300px" }}
          value={url}
          onChange={(e) => setUrl(e.target.value)}
          placeholder="Enter URL"
          required
        />
        <button type="submit" style={{ marginLeft: "10px" }}>
          Analyse
        </button>
      </form>

      {loading && <p>Loading...</p>}

      {result.length > 0 && (
        <table border="1" style={{ marginTop: "20px" }}>
          <thead>
            <tr>
              <th>Word</th>
              <th>Count</th>
              <th>Language</th>
            </tr>
          </thead>
          <tbody>
            {result.map((row, index) => (
              <tr key={index}>
                <td>{row.word}</td>
                <td>{row.count}</td>
                <td>{row.lang}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default App;
