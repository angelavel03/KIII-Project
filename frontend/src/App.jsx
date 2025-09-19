import './App.css'
import {useEffect, useState} from "react";

function App() {

    const [formData, setFormData] = useState({
        title: "",
        content: ""
    });

    const [response, setResponse] = useState(null);

    const [notes, setNotes] = useState([]);

    useEffect(() => {
        // runs once on component mount
        fetch("/api/notes")
            .then((res) => res.json())
            .then((data) => setNotes(data))
            .catch(console.error);
    }, []);

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleDelete = async (e) => {
        await fetch("/api/notes/delete/" + e.target.id, { method: "DELETE" });
        setNotes((prev) => prev.filter(note => note.id !== e.target.id));
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const res = await fetch("/api/notes/add", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(formData),
            });

            if (!res.ok) {
                throw new Error("Request failed");
            }

            const data = await res.json();
            setNotes((prev) => [...prev, data]);
            setResponse({ success: true, message: data.message || "Submitted!" });
        } catch (err) {
            setResponse({ success: false, message: err.message });
        }
    };

    return (
        <div className="container mt-5">
            {response && (
                <div className="alert alert-success mb-3">
                    {response.message}
                </div>
            )}
            <h1>Notes Application</h1>
            <form onSubmit={handleSubmit}>
                <div className="mt-3">
                    <label className="form-label">Title</label>
                    <input
                        name="title"
                        type="text"
                        className="form-control"
                        value={formData.title}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div className="mt-3">
                    <label className="form-label">Content</label>
                    <textarea
                        name="content"
                        className="form-control w-100"
                        value={formData.content}
                        onChange={handleChange}
                        required
                    ></textarea>
                </div>
                <div className="mt-3">
                    <button type="submit" className="btn btn-primary">Create new note</button>
                </div>
            </form>
            <table className="table mt-5">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Content</th>
                        <th>Delete note</th>
                    </tr>
                </thead>
                <tbody>
                    { notes.length > 0 && notes.map((note, index) => (
                        <tr key={index}>
                            <td>{ note.title }</td>
                            <td>{ note.content }</td>
                            <td>
                                <button
                                    type="button"
                                    className="btn btn-danger"
                                    id={ note.id }
                                    onClick={handleDelete}
                                >Delete</button>
                            </td>
                        </tr>
                    )) }
                </tbody>
            </table>
        </div>
    )
}

export default App
