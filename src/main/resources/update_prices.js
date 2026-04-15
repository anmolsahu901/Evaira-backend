const fs = require('fs');

async function checkPrice(url) {
  try {
    const res = await fetch(url, {
      headers: { 'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36' },
      signal: AbortSignal.timeout(6000)
    });
    const text = await res.text();
    // Flipkart
    let m = text.match(/<meta property="og:price:amount" content="([0-9.]+)">/);
    if (m) return parseFloat(m[1]);

    m = text.match(/"price":\s*([0-9.]+)/)
    if (m) return parseFloat(m[1]);

    m = text.match(/"price":\s*"([^"]+)"/)
    if (m) {
      let v = parseFloat(m[1].replace(/,/g, '').replace(/[^\d.]/g, ''));
      if (!isNaN(v) && v > 0) return v;
    }

    // Ajio
    m = text.match(/"price":"₹?([0-9,.]+)"/);
    if (m) {
      let v = parseFloat(m[1].replace(/,/g, ''));
      if (!isNaN(v)) return v;
    }

  } catch (e) { }
  return null;
}

async function run() {
  const data = JSON.parse(fs.readFileSync('dataset.json', 'utf8'));
  let updatedCount = 0;

  // process in batches of 10 to avoid too many concurrent requests
  const batchSize = 10;
  for (let i = 0; i < data.length; i += batchSize) {
    const batch = data.slice(i, i + batchSize);
    await Promise.all(batch.map(async (item) => {
      if (item.deeplinkUrl) {
        const newPrice = await checkPrice(item.deeplinkUrl);
        if (newPrice && typeof newPrice === 'number') {
          if (item.price !== newPrice) {
            item.price = newPrice;
            updatedCount++;
          }
        }
      }
    }));
    // Small delay between batches
    await new Promise(r => setTimeout(r, 500));
  }

  fs.writeFileSync('dataset.json', JSON.stringify(data, null, 2));
  fs.writeFileSync('dataset_temp.json', JSON.stringify(data, null, 2));
  console.log(`Finished processing. Updated ${updatedCount} prices.`);
}

run();
