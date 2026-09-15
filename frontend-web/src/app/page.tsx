'use client';

import { useState, useEffect, FormEvent } from 'react';

type TicketPriority = 'LOW' | 'MEDIUM' | 'HIGH' | 'CRITICAL';
type TicketStatus = 'NEW' | 'IN_PROGRESS' | 'WAITING_ON_USER' | 'RESOLVED' | 'CLOSED';

interface  Ticket {
  id: string;
  subject: string;
  description: string;
  status: TicketStatus;
  priority: TicketPriority;
  slaDeadline: string;
  createdAt: string;
}

const API_BASE_URL = 'http://localhost:8080/api/v1/tickets';

export default function ServiceDeskDashboard() {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [submitting, setSubmitting] = useState<boolean>(false);

  const [subject, setSubject] = useState<string>('');
  const [description, setDescription] = useState<string>('');
  const [priority, setPriority] = useState<TicketPriority>('MEDIUM');

  const fetchTickets = async () => {
    try {
      setLoading(true);
      const res = await fetch(API_BASE_URL);

      if (!res.ok) {
        throw new Error('HTTP error! status: ${res.status}');
      }

      const data: Ticket[] = await res.json();
      setTickets(data);
    } catch (err) {
      console.error('Error fetching tickets:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTickets();
  }, []);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    if (!subject.trim)
  }

  return (
    <main className="min-h-screen bg-zinc-950 text-zinc-100 p-8">
      <div className="max-w-6xl mx-auto space-y-8">
        <header className="border-b border-zinc-800 pb-4">
          <h1 className="text-2xl font-bold">AETHEL Service Desk</h1>
          <p className="text-zinc-400 text-sm">Autonomous Ticket Ingestion & SLA Dashboard</p>
        </header>
        <div className="text-zinc-500 text-sm">
          Skeleton ready. Total tickets in memory: {tickets.length}
        </div>
      </div>
    </main>
  );
}