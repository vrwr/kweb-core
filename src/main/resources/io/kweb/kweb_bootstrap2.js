function toWSUrl(s) {
    let l = window.location;
    return (l.protocol === "https:" ? "wss://" : "ws://") + l.host + "/" + s;
}

